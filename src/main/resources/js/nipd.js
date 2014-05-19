(function($, molgenis) {
	"use strict";

	var self = molgenis.usermanager = molgenis.usermanager || {};

	function getAPrioriRisk(gestAge, matAge, trisomyType) {
		$.ajax({
			type : 'GET',
			url : '/plugin/home/getAPrioriRisk/' + gestAge + '/' + matAge + '/' + trisomyType,
			success : function(text) {
				$('#apriori').val(text);
				$('#apriori').attr('style', 'background-color:#dff0d8'); 
			}
		});
	}
	
	function getRisk(zscore, llim, ulim, apriori, varcof) {
		$.ajax({
			type : 'GET',
			url : '/plugin/home/getRisk/' + zscore + '/' + llim + '/' + ulim + '/' + apriori + '/' + varcof,
			success : function(risk) {
				$('#trisomyChance').html(risk);
				$('#showResult').attr('style', 'display:block');
				$('#apriori').attr('style', ''); 
			}
		});
	}

	$(function() {
		$('#calculate-risk-btn').click(function(e) {
			e.preventDefault();
			e.stopPropagation();			
			
			$('#showResult').attr('style', 'display:none');
			if ($('#llim').val() < 0 || 100 < $('#llim').val()) {
				molgenis.createAlert([{'message': 'Lower limit fetal DNA must have a value between 0 and 100%.'}], 'error');
			} else if ($('#ulim').val() < 0 || 100 < $('#ulim').val()) {
				molgenis.createAlert([{'message': 'Upper limit fetal DNA must have a value between 0 and 100%.'}], 'error');
			} else if ($('#varcof').val() < 0 || 100 < $('#varcof').val()) {
				molgenis.createAlert([{'message': 'Variation coefficient must have a value between 0 and 100%.'}], 'error');
			} else if ($('#zscore').val() < -5 || 40 < $('#zscore').val()) {
				molgenis.createAlert([{'message': 'Observed Z score must have a value -5 and 40.'}], 'error');
			} else if ("" === $('#apriori').val() || $('#apriori').val() < 0 || 1 < $('#apriori').val()) {
				molgenis.createAlert([{'message': 'A priori risk of trisomy must have a value 0 and 1.'}], 'error');
			} else {
				$('#showResult').attr('style', 'display:block');
				$('#results').html(getRisk($('#zscore').val(), $('#llim').val(), $('#ulim').val(), $('#apriori').val(), $('#varcof').val()));
				
				$('#llimResult').html($('#llim').val());
				$('#ulimResult').html($('#ulim').val());
				$('#varcofResult').html($('#varcof').val());
				$('#zscoreResult').html($('#zscore').val());
				$('#aprioriResult').html($('#apriori').val());				
			}
		});
		
		$('#calculate-a-priori-risk-btn').click(function(e) {
			e.preventDefault();
			e.stopPropagation();			
			getAPrioriRisk($('#gestAge').val(), $('#matAge').val(), $('#trisomyType .active').val());
		});
		
	});
}($, window.top.molgenis = window.top.molgenis || {}));