<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>

<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        role = user.getRole()
        isAdmin = user.isAdmin()
        isActive = user.isActive()
        currentUserId = user.getId()
    >
<#else>
    <#assign
        name = ""
        role = false
        isAdmin = false
        isActive = false
        currentUserId = -1
    >
</#if>