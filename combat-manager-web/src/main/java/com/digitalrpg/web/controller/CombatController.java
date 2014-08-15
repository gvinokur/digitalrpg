package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.Combat;
import com.digitalrpg.domain.model.CombatCharacter;
import com.digitalrpg.domain.model.CombatState;
import com.digitalrpg.domain.model.SystemAction;
import com.digitalrpg.domain.model.SystemCombatItem;
import com.digitalrpg.domain.model.SystemCombatItems;
import com.digitalrpg.domain.model.SystemCombatProperties;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.pathfinder.PathfinderCombatProperties;
import com.digitalrpg.web.controller.model.CombatCharacterVO;
import com.digitalrpg.web.controller.model.CreateCombatVO;
import com.digitalrpg.web.controller.model.OrderAndAction;
import com.digitalrpg.web.controller.model.status.CombatCharacterStatusVO;
import com.digitalrpg.web.controller.model.status.CombatStatusVO;
import com.digitalrpg.web.service.CampaignService;
import com.digitalrpg.web.service.CombatService;
import com.digitalrpg.web.service.UserWrapper;
import com.digitalrpg.web.service.combat.ItemAction;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

@Controller
@RequestMapping("/combats")
public class CombatController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CombatService combatService;

    @ModelAttribute("combats")
    public List<Combat> getCombats(@AuthenticationPrincipal UserWrapper user) {
        return ImmutableList.of();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showCombats() {
        return "/combats";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showCreateCombatPage(@RequestParam Long campaignId) {
        Campaign campaign = campaignService.get(campaignId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("campaign", campaign);
        modelMap.put("remainingCharacters", campaign.getCharacters());
        modelMap.put("show_content", "combat_create");
        modelMap.put("createCombatVO", new CreateCombatVO());
        return new ModelAndView("/combats", modelMap);
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public ModelAndView showEditCombatPage(@PathVariable Long id) {
        Combat combat = combatService.getCombat(id);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("campaign", combat.getCampaign());
        modelMap.put("remainingCharacters", combat.getRemainingCharacters());
        modelMap.put("currentCharacters", combat.getCombatCharacters());
        modelMap.put("show_content", "combat_create");
        modelMap.put("createCombatVO", new CreateCombatVO(combat));
        return new ModelAndView("/combats", modelMap);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createCombat(@Valid @ModelAttribute("createCombatVO") CreateCombatVO createCombatVO, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Map<String, Object> modelMap = new HashMap<String, Object>();
            Campaign campaign;

            if (createCombatVO.getId() != null) {
                Combat combat = combatService.getCombat(createCombatVO.getId());
                campaign = combat.getCampaign();
                modelMap.put("remainingCharacters", combat.getRemainingCharacters());
                modelMap.put("currentCharacters", combat.getCombatCharacters());
            } else {
                campaign = campaignService.get(createCombatVO.getCampaignId());
                modelMap.put("remainingCharacters", campaign.getCharacters());
            }
            modelMap.put("campaign", campaign);
            modelMap.put("show_content", "combat_create");
            return new ModelAndView("/combats", modelMap);
        }
        Campaign campaign = campaignService.get(createCombatVO.getCampaignId());
        if (createCombatVO.getId() != null) {
            combatService.editCombat(createCombatVO.getId(), createCombatVO.getName(), createCombatVO.getDescription(),
                    createCombatVO.getPlayers(), createCombatVO.getExtraInfo(), createCombatVO.getCampaignId(),
                    createSystemCombatProperties(campaign.getSystem()));
            redirectAttributes.addFlashAttribute("form_message", "Combat edited");
        } else {
            combatService.createCombat(createCombatVO.getName(), createCombatVO.getDescription(), createCombatVO.getPlayers(),
                    createCombatVO.getExtraInfo(), createCombatVO.getCampaignId(), createSystemCombatProperties(campaign.getSystem()));
            redirectAttributes.addFlashAttribute("form_message", "Combat created");
        }

        return new ModelAndView("redirect:/campaigns/" + createCombatVO.getCampaignId() + "/show");
    }

    private SystemCombatProperties createSystemCombatProperties(SystemType system) {
        switch (system) {
            case Pathfinder:
                PathfinderCombatProperties properties = new PathfinderCombatProperties();
                properties.setRoundsPerTurn(10);
                properties.setTurns(10);
                return properties;

            default:
                break;
        }
        return null;
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public ModelAndView showCombat(@PathVariable Long id) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Combat combat = combatService.getCombat(id);
        modelMap.put("combat", combat);
        if (combat.getState() == CombatState.STARTED) {
            return new ModelAndView("/combat-console", modelMap);
        } else {
            modelMap.put("show_content", "combat_view");
            return new ModelAndView("/combats", modelMap);
        }
    }

    @RequestMapping(value = "/{id}/accept", method = RequestMethod.GET)
    public String acceptCombat(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user, RedirectAttributes redirectAttributes) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user.unwrap())) {
            combatService.acceptCombat(id);
            redirectAttributes.addFlashAttribute("form_message", "Combat marked as Ready");
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Only the GM can mark the combat as ready");
        }
        return "redirect:/combats/" + id + "/show";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteCombat(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user, RedirectAttributes redirectAttributes) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user.unwrap())) {
            combatService.deleteCombat(id);
            redirectAttributes.addFlashAttribute("form_message", "Combat deleted");
            // TODO, get the campaign Id before as this is throwing an NPE.
            return "redirect:/campaigns/" + combat.getCampaign().getId() + "/show";
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Only the GM can delete this combat");
            return "redirect:/combats/" + id + "/show";
        }

    }

    @RequestMapping(value = "/{id}/start", method = RequestMethod.GET)
    public ModelAndView startCombat(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user, RedirectAttributes redirectAttributes) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getActiveCombat() != null) {
            redirectAttributes.addFlashAttribute("error_message", "Cannot start combat, a combat is already active");
            return new ModelAndView("redirect:/campaigns/" + combat.getCampaign().getId() + "/show");
        }
        if (!user.equals(combat.getCampaign().getGameMaster())) {
            redirectAttributes.addFlashAttribute("error_message", "Cannot start combat, only the GM of the Campaign can do that");
            return new ModelAndView("redirect:/campaigns/" + combat.getCampaign().getId() + "/show");
        }
        combatService.startCombat(combat);
        modelMap.put("combat", combat);
        return new ModelAndView("/combat-console", modelMap);
    }

    @RequestMapping(value = "/{id}/console/show", method = RequestMethod.GET)
    public ModelAndView showCombatConsole(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Combat combat = combatService.getCombat(id);
        SystemCombatItems items = combatService.getSystemCombatItems(combat.getCampaign().getSystem());
        modelMap.put("combat", combat);
        modelMap.put("items", items);
        String viewName = null;
        if (combat.getCampaign().getGameMaster().equals(user)) {
            viewName = "/combat-gm";
        } else {
            CombatCharacter<SystemAction> playerCharacter = combatService.getPlayerCharacter(combat, user);
            modelMap.put("playerCharacter", playerCharacter);
            viewName = "/combat-player";
        }
        return new ModelAndView(viewName, modelMap);
    }

    @RequestMapping(value = "/{id}/end", method = RequestMethod.GET)
    public ModelAndView endCombat(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user, RedirectAttributes redirectAttributes) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Combat combat = combatService.getCombat(id);
        String viewName = null;
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combatService.endCombat(combat);
            redirectAttributes.addFlashAttribute("form_message", "Combat " + combat.getName() + " finished");
            viewName = "redirect:/campaigns/" + combat.getCampaign().getId() + "/show";
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Cannot end combat, you must be the GM to end it");
            viewName = "redirect:/combats";
        }
        return new ModelAndView(viewName, modelMap);
    }

    @RequestMapping(value = "/{id}/items/{itemType}", method = RequestMethod.GET)
    public ResponseEntity<List<? extends SystemCombatItem>> getItems(@PathVariable Long id, @PathVariable String itemType) {
        Combat<?> combat = combatService.getCombat(id);
        SystemCombatItems<?> items = combatService.getSystemCombatItems(combat.getCampaign().getSystem());
        return new ResponseEntity<List<? extends SystemCombatItem>>(items.get(itemType), HttpStatus.OK);
    }

    @RequestMapping(value = "/character/{attributeName}", method = RequestMethod.POST)
    public ResponseEntity<?> updateCombatCharacterAttribute(@PathVariable String attributeName, @RequestParam("pk") Long id,
            @RequestParam("value") String value, @AuthenticationPrincipal UserWrapper user) {
        try {
            CombatCharacterVO vo = combatService.updateCombatCharacter(id, attributeName, value, user);
            return new ResponseEntity<CombatCharacterVO>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Cannot update data", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/character/{itemType}/{action}", method = RequestMethod.POST)
    public ResponseEntity<?> updateCombatCharacterItem(@PathVariable String itemType, @PathVariable ItemAction action,
            @RequestParam("pk") Long id, @RequestParam("itemId") Long itemId, @AuthenticationPrincipal UserWrapper user) {
        try {
            CombatCharacterStatusVO vo = combatService.updateCombatCharacterItem(id, itemType, action, itemId, user);
            return new ResponseEntity<CombatCharacterStatusVO>(vo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Cannot update data", HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "{id}/character/next", method = RequestMethod.POST)
    public ResponseEntity<?> advanceCharacter(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combat = combatService.nextCharacter(id);
            return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
        } else {
            return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "{id}/character/previous", method = RequestMethod.POST)
    public ResponseEntity<?> previousCharacter(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combat = combatService.previousCharacter(id);
            return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
        } else {
            return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "{id}/character/{characterId}/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCharacter(@PathVariable Long id, @PathVariable Long characterId, @AuthenticationPrincipal UserWrapper user) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combat = combatService.addCombatant(combat, characterId);
            return new ResponseEntity(combatService.getStatus(combat, true, user.unwrap()), HttpStatus.OK);
        } else {
            return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "{id}/character/{characterId}/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCharacter(@PathVariable Long id, @PathVariable Long characterId,
            @AuthenticationPrincipal UserWrapper user) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combatService.deleteCombatant(combat, characterId);
            // Reload changes
            combat = combatService.getCombat(combat.getId());
            return new ResponseEntity(combatService.getStatus(combat, true, user.unwrap()), HttpStatus.OK);
        } else {
            return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "{id}/character/orderAndAction", method = RequestMethod.POST)
    public ResponseEntity<?> updateOrderAndActions(@PathVariable Long id, @RequestBody Map<Long, OrderAndAction> charactersOrderAndActions,
            @AuthenticationPrincipal UserWrapper user) {
        Combat combat = combatService.getCombat(id);
        if (combat.getCampaign().getGameMaster().equals(user)) {
            combat = combatService.updateOrderAndActions(id, charactersOrderAndActions);
            return new ResponseEntity(combatService.getStatus(combat), HttpStatus.OK);
        } else {
            return new ResponseEntity("Only the game master can update the current user", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/characters/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CombatCharacterVO getCombatCharacterData(@PathVariable Long id) {
        CombatCharacter<?> combatCharacter = combatService.getCombatCharacter(id);
        return CombatService.combatCharacterToVOfunction.apply(combatCharacter);
    }

    @RequestMapping(value = "/{id}/status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CombatStatusVO> getCurrrentStatus(@PathVariable Long id, @AuthenticationPrincipal UserWrapper user) {
        Combat<?> combat = combatService.getCombat(id);
        CombatStatusVO status = combatService.getStatus(combat, true, user.unwrap());
        // TODO: Verify if changed, then send only if modified. (Use Last-Modified,
        // If-Modified-Since or ETag headers)
        return new ResponseEntity<CombatStatusVO>(status, HttpStatus.OK);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/{id}/nonPlayingCombatants", method = RequestMethod.GET)
    @ResponseBody
    public Map<Long, String> getNonPlayingCombatants(@PathVariable Long id) {
        Combat combat = combatService.getCombat(id);

        Campaign campaign = combat.getCampaign();
        Collection<SystemCharacter> playingCharacter =
                Collections2.transform(combat.getCombatCharacters(), new Function<CombatCharacter, SystemCharacter>() {
                    @Override
                    public SystemCharacter apply(CombatCharacter input) {
                        return input.getCharacter();
                    }
                });
        Builder<Long, String> builder = ImmutableMap.builder();
        // TODO: Check the best way to handle this
        // for(SystemCharacter c : combat.getCampaign().getNonPlayerCharacters()) {
        // if(!playingCharacter.contains(c)) {
        // builder.put(c.getId(), c.getCharacter().getName());
        // }
        // }
        // for(SystemCharacter c : combat.getCampaign().getPlayerCharacters()) {
        // if(!playingCharacter.contains(c)) {
        // builder.put(c.getId(), c.getCharacter().getName());
        // }
        // }
        return builder.build();
    }


}
