latestProduct.name=${latestProduct.name}
user=${user}
company=${company}
nodd=${nodd}
nodd.name=${nodd.name}
ccs=${ccs}
ccs.name=${ccs.name}
ccs.age=${ccs.age}

<#--ccs.name=${ccs.name}-->

<#if user == "aas">
    enter if user == aas
</#if>

    ls:
    <#list lis as lll>
        ${lll}
    </#list>

<#macro greet person color>
    person=${person}
    color=${color}
</#macro>

<@greet person="Fred" color="black"/>

