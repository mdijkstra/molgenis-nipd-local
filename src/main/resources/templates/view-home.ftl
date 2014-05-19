<#include "nipd_macro.ftl">

<#include "molgenis-header.ftl">
<#include "molgenis-footer.ftl">

<#assign css=['nipd.css']>
<#assign js=['nipd.js']>

<@header css js/>

<div class="container-fluid">
	<div class="row-fluid">
		<H1>Non-invasive prenatal diagnostics</H1>
	</div>
	<div class="row-fluid">
		<div class="span5" style="background-color:#E8EAEB;">
			<h2>Input</h2>
			<table class="table" border=10 bordercolor="#E8EAEB">
			<tbody>
			<@tableRow "llim" "" "Lower limit fetal DNA" "2" "%" "" />
			<@tableRow "ulim"  "" "Upper limit fetal DNA" "30" "%" "" />
			<@tableRow "varcof"  "" "Variation coefficient" "0.5" "%" "" />
			<@tableRow "zscore"  "" "Observed Z score" "4" "" "" />
			<@tableRow "apriori"  "" "A priori risk of trisomy (0...1)" "" "" "<button id='calculate-risk-btn' type='submit' class='btn btn-primary'><B>Go</B> <i class='icon-chevron-right icon-white'></i></button>" />
			<tr bgcolor='#bce8f1'>
				<td colspan="2"><p><i>The a priori risk may be estimated based on maternal/gestational age and the type of trisomy.</i></p></td>
				<td></td>
			</tr>
			<@tableRow "gestAge" "bgcolor='#bce8f1'"   "Gestational age" "" "weeks" "" />
			<@tableRow "matAge" "bgcolor='#bce8f1'"  "Maternal age" "" "years" "" />
			<tr class="muted" bgcolor='#bce8f1'>
				<td class="tdLabel" style="text-align:right">Type</td>
				<td>
					<div id="trisomyType" class="btn-group" data-toggle="buttons-radio">
						<button type="button" class="btn btn-default active" value="13">T13</button>
						<button type="button" class="btn btn-default" value="18">T18</button>
						<button type="button" class="btn btn-default" value="21">T21</button>
					</div>
				</td>
				<td><button id='calculate-a-priori-risk-btn' type='submit' class='btn btn-info'><B>Go</B> <i class='icon-chevron-right icon-white'></i></button></td>
			</tr>
			</tbody>
			</table>
		</div>
	
		<div class="span7">
			<h2>Results</h2>
			<div id="showResult" style="display:none">
				<p>The chance on a trisomy is estimated <b style="color: #F00;">1 in <span id="trisomyChance"></span></b>, given your inputs:</p>
				<ul class="muted">
					<li>Lower limit fetal DNA: <span id="llimResult"></span></li>
					<li>Upper limit fetal DNA: <span id="ulimResult"></span></li>
					<li>Variation coefficient: <span id="varcofResult"></span></li>
					<li>Observed z-score: <span id="zscoreResult"></span></li>
					<li>A priori risk: <span id="aprioriResult"></span></li>
				</ul>
			</div>
		</div>		
	</div>

	<div class="row-fluid">
		<P CLASS="muted">Please contact G.J. te Meerman for further information via <I>g.j.te.meerman@umcg.nl</I>.</P>
	</div>	
</div>

<#-- why problem with variable in @footer/ ??-->
</body>
</html>

