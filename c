[33mcommit ecc08e1884e2929c2c30c4d79219889924c0e798[m
Author: Danilo <Danilo>
Date:   Tue Apr 22 10:57:49 2014 -0300

    filtro das funcoes com a classificacao = NSYS

[1mdiff --git a/src/main/java/nelsys/modelo/Funcao.java b/src/main/java/nelsys/modelo/Funcao.java[m
[1mindex 1da57b3..f961870 100644[m
[1m--- a/src/main/java/nelsys/modelo/Funcao.java[m
[1m+++ b/src/main/java/nelsys/modelo/Funcao.java[m
[36m@@ -11,6 +11,14 @@[m [mpublic class Funcao {[m
 	private String cdchamada;[m
 	private String nmfuncao;[m
 	private String tpfuncao;[m
[32m+[m	[32mprivate String cdclassificacao;[m
[32m+[m[41m	[m
[32m+[m	[32mpublic void setCdclassificacao(String cdclassificacao) {[m
[32m+[m		[32mthis.cdclassificacao = cdclassificacao;[m
[32m+[m	[32m}[m
[32m+[m	[32mpublic String getCdclassificacao() {[m
[32m+[m		[32mreturn cdclassificacao;[m
[32m+[m	[32m}[m
 	public String getIdfuncao() {[m
 		return idfuncao;[m
 	}[m
[1mdiff --git a/src/main/java/nelsys/repository/FuncaoRepository.java b/src/main/java/nelsys/repository/FuncaoRepository.java[m
[1mindex 77648ff..81202d2 100644[m
[1m--- a/src/main/java/nelsys/repository/FuncaoRepository.java[m
[1m+++ b/src/main/java/nelsys/repository/FuncaoRepository.java[m
[36m@@ -17,7 +17,7 @@[m [mpublic class FuncaoRepository {[m
 	[m
 	@SuppressWarnings("unchecked")[m
 	public List<Funcao> lista(){[m
[31m-		return entityManager.createQuery("from Funcao f ").getResultList();[m
[32m+[m		[32mreturn entityManager.createQuery("from Funcao f where f.cdclassificacao like 'NSYS%' ").getResultList();[m
 	}[m
 	public Funcao findById(String idfuncao){[m
 		return entityManager.find(Funcao.class, idfuncao);[m
[1mdiff --git a/target/classes/META-INF/maven/nelsys/valetao/pom.properties b/target/classes/META-INF/maven/nelsys/valetao/pom.properties[m
[1mindex abd7966..6ee1bc7 100644[m
[1m--- a/target/classes/META-INF/maven/nelsys/valetao/pom.properties[m
[1m+++ b/target/classes/META-INF/maven/nelsys/valetao/pom.properties[m
[36m@@ -1,5 +1,5 @@[m
 #Generated by Maven Integration for Eclipse[m
[31m-#Mon Apr 21 23:22:43 GMT-03:00 2014[m
[32m+[m[32m#Tue Apr 22 10:45:38 GMT-03:00 2014[m
 version=0.1.0[m
 groupId=nelsys[m
 m2e.projectName=valetao[m
[1mdiff --git a/target/classes/nelsys/modelo/Funcao.class b/target/classes/nelsys/modelo/Funcao.class[m
[1mindex 63462cd..e434833 100644[m
Binary files a/target/classes/nelsys/modelo/Funcao.class and b/target/classes/nelsys/modelo/Funcao.class differ
[1mdiff --git a/target/classes/nelsys/repository/FuncaoRepository.class b/target/classes/nelsys/repository/FuncaoRepository.class[m
[1mindex 92792f5..1b2d988 100644[m
Binary files a/target/classes/nelsys/repository/FuncaoRepository.class and b/target/classes/nelsys/repository/FuncaoRepository.class differ