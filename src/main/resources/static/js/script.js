$(document).ready(function(){
	
		var titulo = new String();
		var tipolancamento = new String();
		var produtos;
		$("#loading").hide();
		
		
		
		$("#loading").css({
			 position : 'absolute',
			 top : '50%',
			 left: '50%',
			 color : 'red'
		});
	  
	
	 $("#funcao").change(function(){
		 var idfuncao = new String(this.value);
		 $("#loading").show();
		  $.ajax({
			  data: {idfuncao : idfuncao},
				  url:"grupo?",success:function(result){
					  $("#loading").hide();
					  	$("#grupos").html(result);
					  		
				  }});
		 
	 });
	 $("#funcaobonus").change(function(){
		 
		 if($("#duplica").is(':checked')){
			 $("#loading").show();
			  $.ajax({
				  data: {idfuncao : this.value},
				  url:"grupobonusduplica?",success:function(result){
				  $("#loading").hide();
				 $("#grupos").html(result);
				
				  }});
			 
		 }
		 else {
		 
		 $("#loading").show();
		  $.ajax({
			  url:"grupobonus?",success:function(result){
			  $("#loading").hide();
			 $("#grupos").html(result);
			
			  }});
		 }
	 });
	
	 $("#processa").click(function(){
		  alert('teste');
	 });	  
	$("#filtrafechamento").click(function(){
		
		var fech = document.getElementById("dtfechamento");
		var ref = document.getElementById("dtreferencia");
		var data = document.getElementById("data");
		if(!(fech.checked || ref.checked)){
			bootbox.alert("Selecione o Tipo de Data!");
			return false;
		}
		else{
			if(validaPeriodo()){
				var tipo = new String();
					if(fech.checked){
					tipo = fech.value;
					}
					else{
					tipo = ref.value;
					}
				
			
		$("#loading").show();
		$.ajax({
			data: {tipo : tipo, data : data.value },
			url:"fechamentosfiltro",success:function(result){
				$("#loading").hide();
				 $("#resultadofechamentos").html(result);
				
		}});
			
		}
		}
	});
	$("#representante").keyup(function(){
		$("#loading").show();
		 $.ajax({url:"consultarepresentante?nome="+this.value,success:function(result){
			 $("#loading").hide();
			 $("#autocompleterepresentante").html(result);
			 
			  }});
	});
	
	$('#duplicar').click(function(){
		alert('teste');
	});
	$("#home").click(function(){
		
		window.location="/";
	});
	$('#fechamentos').click(function(){
		window.location="/fechamentos";
	});
	$('#regra').click(function(){
		window.location="/regra";
	});
	$('#bonus').click(function(){
		window.location="/bonus";
	});
	$('#paginacao').click(function(){
		window.location="pagina/1";
	});
	$("#configuracao").click(function(){
		
		window.location="/configuracao";
	});
	$('#btSalva').click(function(){
		
		tipolancamento = $("#tipolancamento").val();
		
		var valor = document.getElementById('valorlancamento');
		var data = document.getElementById('datalancamento');
		var historico = document.getElementById('historicolancamento');
		var empresa = document.getElementById('empresa');
		//alert(valor.value+' - '+data.value+" - "+historico.value+ " - "+tipolancamento);
		var elementoidpessoa = document.getElementById('idpessoa');
		var idpessoa = new String(elementoidpessoa.value);
		$.ajax({
			data: {idpessoa : idpessoa,data : data.value,valor : valor.value,historico : historico.value ,
				tipo : tipolancamento,empresa : empresa.value},
			url:"lancamento",
			success:function(){
				alert('Lançamento Efetuado com Sucesso!');
				valor.value = '';
				data.value = '';
				historico.value = '';
			},
			error:function(){
				alert('Aconteceu algum erro');
			}
			
		});
		
	
	});
	$('#btfiltro').click(function(){
		return validaFiltro();
	});
	$('#geraRelatorio').click(function(){
		
		if(validaTipo()== false){
			return false;	
		}
		else
		if(validaPeriodo()==false){
			return false;
		}
		document.form.submit(); 
	});
	
});



//validações
function validaFiltro(){
	var elemento = document.getElementsByName('filtro');
	var filtro = null;
	for(var i=0;i<elemento.length;i++){
	//bootbox.alert("Existem campos em branco Verifique! ");
		if(elemento[i].checked==true){
			var nmproduto = document.getElementById('descFiltro');
			var desc = setaDescricaoFiltro(elemento[i].value);
			nmproduto.innerHTML = desc;
			filtro = elemento[i].value; 
		}
	}
	if(filtro == null){
		bootbox.alert('Nenhum Filtro foi informado. Verifique');
		return false;
	}
}
function validaTipo(){
	var elemento = document.getElementsByName('tipo');
	var filtro = null;
	for(var i=0;i<elemento.length;i++){
	//bootbox.alert("Existem campos em branco Verifique! ");
		if(elemento[i].checked==true){
			filtro = elemento[i].value; 
		}
	}
	if(filtro == null){
		bootbox.alert('O tipo não foi informado. Verifique');
		return false;
	}
}
function validaPeriodo(){
	var data = document.getElementById('data').value;
	if(data == '')
	{
		bootbox.alert('A Data não foi informada. Verifique');
		return false;
	}
	else {
		return true;
	}
	
}
function validaFuncao(){
	var funcao = document.getElementById('funcao').value;
	if(funcao == '')
	{
		bootbox.alert('O Representante/Funcionário deve ter uma Função Vinculada. Verifique!');
		return false;
	}
	else {
		return true;
	}
	
}
function confirmafechamento(){
	if(validaPeriodo() && validaFuncao()){
		var representante = $("#selecionado");
		var data = $("#data");
		var idpessoa = new String($("#idpessoa").val());
		bootbox.confirm('Confirma Efetuar o Fechamento da comissão do(a) '+
				representante.val()+', data Final: '+data.val()+' ?'
				, function(result){
			if(result){
				$("#loading").show();
			$.ajax({
				data: {idpessoa : idpessoa, data : data.val() },
				url:"fechamento",success:function(result){
					$("#loading").hide();
					alert(result);
					limpadadoshome();
					
			}});
			}
		}
			

);
		
	}
	
}
function limpadadoshome(){
	$("#resultadofiltrorepfunc").html('');
	$("#resultadotabela").html('');
	$("#selecionado").text('');
	$("#funcao").text('');
	$("#data").text('');
}
function confirmaReabertura(x){
	bootbox.confirm('Confirma Efetuar Reabertura do Fechamento selecionado?', function(result){
	if(result){
		document.location = '/reabre/'+x;
	}
	});
	return false;
	}
function selecionarepfunc(x){
	$.ajax({url:"resultadofiltrorepresentante?nome="+x,success:function(result){
		$("#autocompleterepresentante").html(""); 
		$("#resultadofiltrorepfunc").html(result);
		  }});
	
}


function processa(){
	
	if(validaPeriodo()&& validaFuncao()){
	var elementoidpessoa = document.getElementById('idpessoa');
	var idpessoa = new String(elementoidpessoa.value);
	var elementodata = document.getElementById('data');
	var data = new String(elementodata.value);
	$("#loading").show();
	$.ajax({
		
		data: {idpessoa : idpessoa,data : data },
		url:"processa?",success:function(result){
			$("#loading").hide();
		$("#resultadotabela").html(result);
		
		  }});
	}
}
function aplica(x){
	
	var produtos = document.getElementsByName('bonus');
	var bonusprodutos = document.getElementsByName('bonus_produto');
	var tamanho = produtos.length;
	for(var i=0;i < tamanho;i++){
		produtos[i].value = x;
		bonusprodutos[i].value = x+'_'+bonusprodutos[i].value; 
	}
	
}

function aplicapercentual(x){
	var produtos = document.getElementsByName('comissao');
	var tamanho = produtos.length;
	for(var i=0;i < tamanho;i++){
		produtos[i].value = x;
	}
	
	
}
function convertehidden(x){
	
	
	var array = x.split('_'); 
	
	$('#_'+array[1]).val(x);
}

function verifica(){
	if(window.event.keyCode == 13){
		bootbox.confirm('Confirma Alterar o percentual geral?', function(result){
			if(result){
				document.form.submit();
			}
		});
	}

	return false;
	
}
function confirmaAlteraPercentual(){	
		bootbox.confirm('Confirma  a operação?', function(result){
				if(result){
					document.form.submit();
				}
		});
}

	function confirmaAlteraPercentualBonus(){
		
		var grupo =  document.getElementById('gruposelect').value;
		if(grupo != ''){
			bootbox.confirm('Confirma  a operação?', function(result){
					if(result){
						document.form.submit();
					}
				});
		}
}
