package org.mrbag.LinkCompresor.Controller;

import org.mrbag.LinkCompresor.LinkRepository;
import org.mrbag.LinkCompresor.Entity.KeyLinkAttach;
import org.mrbag.LinkCompresor.Entity.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/*"})
public class UIController {

	LinkRepository repo;
	
	String url;
	
	@Value("${app.domain}")
	public void setUrl(String url) {
		this.url = String.format("https://%s/", url);
	}
	
	@Autowired
	public void setRepo(LinkRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping(path = "/")
	public String getMainPageHTML(Model model) {
		model.addAttribute("url", url);
		
		return "Index";
	}
	
	
	@GetMapping("/{id}")
	public String redirectToPageConntroller(@PathVariable("id") String id, Model model) {
		if(id.length() != 5)
			return "redirect:/";
	
		KeyLinkAttach alink = repo.get(id);
		
		if(alink.isValid()) {
			Link l = alink.getLink();
			model.addAttribute("Link", l);
			if(l.isAd()) {
				return "AdLink";
			}
			if(l.isSkeep()) {
				return "redirect:" + l.getLink();
			}
		}
		
		return "redirect:/error?type=\"ID NOT VALID\"";
	}
	
}
