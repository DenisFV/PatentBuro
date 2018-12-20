<#import "parts/common.ftl" as c>
<#import "parts/requestList.ftl" as l>
<#import "parts/requestEdit.ftl" as e>

<@c.page>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Поиск по типу">
                <button type="submit" class="btn btn-secondary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <#if user.getRole()=='USER'>
        <@e.requestEdit "/main" />
    </#if>
    <@l.requestList user!/>

</@c.page>