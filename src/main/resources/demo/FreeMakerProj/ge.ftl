<#assign com=com_json?eval />
<#assign ge1=ge1_json?eval />

com.name=${com.name}
ge1.name=${ge1.name}


ge1.age=${ge1.age}
ge1.ids=${ge1.ids[0]}
<#list ge1.ids as id>
${id}
</#list>
com3=${com3}
ge3=${ge3}

com2.name=${com2.name}
ge2.name=${ge2.name}




