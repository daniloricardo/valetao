$(document).ready(function(){
	$("#nmproduto").keyup(function(){
		
		 $.ajax({url:"produto?nome="+this.value,success:function(result){
			 $("#produtoResultado").html(result);
			  }});
		
	});
})