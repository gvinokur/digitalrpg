package com.digitalrpg.web.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.BatchUpdateException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalrpg.domain.dao.CampaignDao;
import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.controller.model.CampaignVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.MailService.MailType;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class CampaignService {

    @Autowired
    private CampaignDao campaignDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailService mailService;

    public static Function<Campaign, CampaignVO> campaignToVOFunction = new Function<Campaign, CampaignVO>() {
        public CampaignVO apply(Campaign in) {
            CampaignVO out = new CampaignVO();
            out.fromCampaign(in);
            return out;
        }
    };

    public Campaign createCampaign(String name, String description, User gm, Boolean isPublic, SystemType system) {
        return campaignDao.createCampaign(name, description, gm, isPublic, system);
    }


    public Campaign editCampaign(Long id, String name, String description, User gm, Boolean isPublic) {
        // TODO Auto-generated method stub
        return campaignDao.editCampaign(id, name, description, gm, isPublic);
    }

    public List<Campaign> getCampaignsForUser(User user) {
        return campaignDao.getCampaignsForUser(user);
    }

    public Campaign get(Long id) {
        return campaignDao.get(id);
    }

    public Collection<Campaign> search(String searchString, int offset, int limit) {
        return campaignDao.search(searchString, offset, limit);
    }

    public MessageVO requestInvite(Long id, User user) {
        Campaign campaign = campaignDao.get(id);
        MessageVO message = messageService.requestJoin(user, campaign);
        return message;
    }

    public MessageVO acceptRequest(Long id, Long requestId, User user) {
        Campaign campaign = campaignDao.get(id);
        try {
            campaignDao.joinCampaign(id, user);
        } catch (Exception e) {
            if (e instanceof BatchUpdateException) {
                ((BatchUpdateException) e).getNextException().printStackTrace();
            }
            throw e;
        }
        return messageService.acceptRequest(requestId, campaign, user);
    }



    public Map<String, Object> inviteWithUsernameOrEmai(Long id, User user, String usernameOrEmail, String customMessage, String contextPath) {
        Campaign campaign = campaignDao.get(id);
        Builder<String, Object> builder = ImmutableMap.builder();
        boolean validEmail = EmailValidator.getInstance().isValid(usernameOrEmail);
        User userTo;
        if (!validEmail) {
            userTo = userDao.get(usernameOrEmail);
        } else {
            userTo = userDao.findByMail(usernameOrEmail);
        }
        if (!validEmail && userTo == null) {
            builder.put("valid", false).put("message", "Invalid mail or user doesn't exists.");
        } else if (campaign == null) {
            builder.put("valid", false).put("message", "Invalid campaign Id.");
        } else {
            MessageVO message = messageService.invite(user, validEmail ? usernameOrEmail : userTo.getEmail(), userTo, campaign);
            sendMail(user, validEmail ? usernameOrEmail : userTo.getEmail(), contextPath, campaign, message, customMessage);
            builder.put("valid", true);
        }

        return builder.build();
    }

    public boolean invite(Long id, User from, String emailTo, String contextPath) {
        Campaign campaign = campaignDao.get(id);
        if (campaign != null) {
            User userTo = userDao.findByMail(emailTo);
            MessageVO message = messageService.invite(from, emailTo, userTo, campaign);
            sendMail(from, emailTo, contextPath, campaign, message, null);
            return true;
        }
        return false;
    }



    private void sendMail(final User from, final String email, final String contextPath, final Campaign campaign,
            final MessageVO inviteMessage, final String additionalMessage) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("email", email);
        model.put("username", from.getName());
        try {
            model.put("contextPath", new URI(contextPath));
        } catch (URISyntaxException ignore) {}
        model.put("campaign", campaign);
        model.put("message", inviteMessage);
        model.put("additionalMessage", additionalMessage);

        mailService.sendMail(" [Digital RPG] Join my campaign:  \"" + campaign.getName() + "\".", from, ImmutableList.of(email), model,
                MailType.INVITE_TO_CAMPAIGN);
    }



}
