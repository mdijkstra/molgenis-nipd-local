package org.molgenis.nipd.controller;

import static org.molgenis.nipd.controller.HomeController.URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.molgenis.framework.ui.MolgenisPluginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that handles home page requests
 */
@Controller
@RequestMapping(URI)
public class HomeController extends MolgenisPluginController
{
	public static final String ID = "home";
	public static final String URI = MolgenisPluginController.PLUGIN_URI_PREFIX + ID;

	public HomeController()
	{
		super(URI);
	}

	@RequestMapping
	public String init()
	{
		return "view-home";
	}

	@RequestMapping(value = "getRisk/{zscore}/{llim}/{ulim}/{apriori}/{varcof:.+}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getRisk(@PathVariable("zscore") String zscore, @PathVariable("llim") String llim,
			@PathVariable("ulim") String ulim, @PathVariable("apriori") String apriori,
			@PathVariable("varcof") String varcof) throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("./trisomy_risk", zscore, llim, ulim, apriori, varcof);
		Process p = pb.start();
		try
		{
			p.waitFor();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		// return value x means chance of 1 in x. Therefore, we want to round x before it's returned.
		try
		{
			return "" + Math.round(Double.valueOf(stdInput.readLine().trim()));
		}
		catch (Exception e)
		{
			return "Something went wrong!";
		}
	}

	@RequestMapping(value = "getAPrioriRisk/{gestationalAgeWeeks}/{maternalAgeYears}/{trisomyType}", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAPrioriRisk(@PathVariable("gestationalAgeWeeks") String gestationalAgeWeeks,
			@PathVariable("maternalAgeYears") String maternalAgeYears, @PathVariable("trisomyType") String trisomyType)
			throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("./trisomy_a_priori_risk", gestationalAgeWeeks, maternalAgeYears,
				trisomyType);
		Process p = pb.start();
		try
		{
			p.waitFor();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		// return value x means a chance of 1 in x; but we want to return 1/x
		try
		{
			Double chance = 1 / Double.valueOf(stdInput.readLine().trim());

			return "" + chance;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	@RequestMapping(value = "/go", method = RequestMethod.POST)
	public String doit(@RequestParam("mage") String mage, Model model) throws IOException
	{

		System.out.println(">> Working Directory = " + System.getProperty("user.dir"));

		ProcessBuilder pb = new ProcessBuilder(
				"/Users/mdijkstra/Documents/git/molgenis-nipd/src/main/resources/tools/test.sh", mage);
		Process p = pb.start();
		try
		{
			p.waitFor();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		model.addAttribute("aaa", "Martijn___" + stdInput.readLine());

		return "view-home";
	}
}
