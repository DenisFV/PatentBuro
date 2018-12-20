<#macro requestList user>
    <#include "security.ftl">

    <div class="card-columns">
        <#if user.getRole()!="USER">
            <#list requests as request>
                <div class="card bg-light my-3">
                    <div class="card-header">${request.id}. ${request.authorName}</div>
                    <div class="card-body">
                        <h5 class="card-title">${request.ptype}</h5>
                        <p class="card-text">${request.text}</p>

                        <ul class="list-group list-group-flush">
                            <#list checks as check>
                                <li class="list-group-item">
                                    <div class="ml-2">
                                        <label for="${check}" class="form-check-label" onclick='window.event.returnValue=false'>
                                            <input id="${check}" type="checkbox" class="form-check-input"
                                                   name="${check}" ${request.checks?seq_contains(check)?string("checked","")}
                                            >
                                            <#if check=="CHECK">Проверено</#if>
                                            <#if check=="VERIFIED">Верифицировано</#if>
                                            <#if check=="PAID">Оплачено</#if>
                                        </label>
                                    </div>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <div class="text-right">
                        <a class="btn btn-secondary-info" style="display: block; margin-left: auto;"
                           href="/check?request=${request.id!}">
                           Подронее
                        </a>
                    </div>
                </div>
            <#else>
                Таких заявок нет
            </#list>
        <#else>
            <#list requests as request>
                <div class="card bg-light my-3">
                    <#if user.getRole()!="USER" || request.authorName==user.getUsername()>
                        <div class="card-header">${request.id}. ${request.authorName}</div>
                        <div class="card-body">
                            <h5 class="card-title">${request.ptype}</h5>
                            <p class="card-text">${request.text}</p>

                            <ul class="list-group list-group-flush">
                            <#list checks as check>
                                <li class="list-group-item">
                                    <div class="ml-2">
                                        <label for="${check}" class="form-check-label" onclick='window.event.returnValue=false'>
                                            <input id="${check}" type="checkbox" class="form-check-input"
                                                   name="${check}" ${(request.checks!)?seq_contains(check)?string("checked","")}
                                            >
                                            <#if check=="CHECK">Проверено</#if>
                                            <#if check=="VERIFIED">Верифицировано</#if>
                                            <#if check=="PAID">Оплачено</#if>
                                        </label>
                                    </div>
                                </li>
                            </#list>
                            </ul>
                        </div>
                        <div class="text-right">
                            <a class="btn btn-secondary-info" style="display: block; margin-left: auto;"
                               href="/user-requests/${request.author.id!}?request=${request.id!}">Изменить</a>
                        </div>
                    </#if>
                </div>
            <#else>
                Таких заявок нет
            </#list>
        </#if>
    </div>
</#macro>