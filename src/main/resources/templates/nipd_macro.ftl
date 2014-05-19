<#macro tableRow inputId rowElt label val unit button>
		<tr class="muted" ${rowElt}>
			<td class="tdLabel" style="text-align:right">${label}</td>
			<td><input id="${inputId}" type="number" step="any" class="span3" value="${val}"> ${unit}</td>
			<td class="span2">${button}</td>
		</tr>
</#macro>