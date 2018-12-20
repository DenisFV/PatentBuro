<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a class="navbar-brand" href="/">RisePatent</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/">Home</a>
            <#if user??>
            <#if isActive>
                <a class="nav-item nav-link" href="/main">Menu</a>
            </#if>
            </#if>
            <#if isAdmin>
                <a class="nav-item nav-link" href="/user">User List</a>
            </#if>
            <#if isActive>
                <a class="nav-item nav-link" href="/user/profile">Profile</a>
            </#if>

        </div>
    </div>
    <div class="navbar-text mr-3">${name}</div>
    <@l.logout isActive/>
</nav>