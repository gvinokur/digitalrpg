package com.digitalrpg.web.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.dao.CharacterDao;
import com.digitalrpg.domain.model.Campaign;
import com.digitalrpg.domain.model.SystemType;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.characters.NonPlayerCharacter;
import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.web.controller.model.CharacterVO;
import com.digitalrpg.web.controller.model.CreateCharacterVO;
import com.digitalrpg.web.controller.model.PathfinderCharacterVO;
import com.digitalrpg.web.service.CampaignService;
import com.google.common.base.Function;

@Controller
@RequestMapping("/monsters")
public class MonsterController {

	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private CampaignService campaignService;
	
	public static final Function<SystemCharacter, CharacterVO> characterToVOfunction = new Function<SystemCharacter, CharacterVO>() {
	
		public CharacterVO apply(SystemCharacter character) {
			CharacterVO characterVO = null;
			if(character instanceof PathfinderCharacter) {
				characterVO = createPathfinderCharacterVO((PathfinderCharacter)character);
			}
			if(characterVO != null) {
				characterVO.setName(character.getCharacter().getName());
				characterVO.setDescription(character.getCharacter().getDescription());
				characterVO.setPictureUrl(character.getCharacter().getPictureUrl());
				characterVO.setId(character.getId());
				characterVO.setCampaign(CampaignService.campaignToVOFunction.apply(character.getCampaign()));
			}
			return characterVO;
		}

		private CharacterVO createPathfinderCharacterVO(
				PathfinderCharacter character) {
			PathfinderCharacterVO vo = new PathfinderCharacterVO();
			vo.setRace(character.getRace());
			vo.setCharacterClass(character.getCharacterClass());
			vo.setHp(character.getHp());
			vo.setCharisma(character.getCharisma());
			vo.setConstitution(character.getConstitution());
			vo.setDexterity(character.getDexterity());
			vo.setIntelligence(character.getIntelligence());
			vo.setStrength(character.getStrength());
			vo.setWisdom(character.getWisdom());
			return vo;
		}
	};
	
	@ModelAttribute("characters")
	public Collection<SystemCharacter> getUserCharacters(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return characterDao.getUserMonsters(user.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String showPlayerCharacters(){
		return "monsters";
	}
	
	@RequestMapping(value = "/create")
	public ModelAndView showCreatePlayerCharacterPage(@RequestParam Long campaignId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("campaign", CampaignService.campaignToVOFunction.apply(campaignService.get(campaignId)));
		modelMap.put("show_content", "create_character");
		return new ModelAndView("/monsters", modelMap );
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createPlayerCharacter(@Valid @ModelAttribute("character") CreateCharacterVO character, BindingResult result, Principal principal, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return new ModelAndView("monsters", "show_content", "create_character");
		}
		User user = (User) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		Campaign campaign = campaignService.get(character.getCampaignId());
		NonPlayerCharacter playerCharacter = characterDao.createNonPlayerCharacter(character.getName(), character.getPictureUrl(), 
				character.getDescription(), false, user);
		if(SystemType.Pathfinder == campaign.getSystem()) {
			characterDao.createPathfinderCharacter(playerCharacter, character.getPathfinder().toPathfinderProperties(), campaign);
		}
		attributes.addFlashAttribute("form_message", "Monster " + character.getName() + " created!");
		return new ModelAndView("redirect:/campaigns/"+campaign.getId()+"/show");
	}
	
	@RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
	public ModelAndView showCharacter(@PathVariable Long id, Principal principal) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		SystemCharacter systemCharacter = characterDao.get(id);
		modelMap.put("character", characterToVOfunction.apply(systemCharacter));
		modelMap.put("show_content", "view_character");
		return new ModelAndView("/monsters", modelMap );
	}
	

}