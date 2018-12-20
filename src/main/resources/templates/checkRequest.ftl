<#import "parts/common.ftl" as c>

<@c.page>
<form action="/check" method="post">
    <div class="card bg-light my-3">
        <div class="card-header">${request.id}. ${request.authorName}</div>
        <div class="card-body">
            <h5 class="card-title">${request.ptype}</h5>
            <p class="card-text">${request.text}</p>

            <div class="container">
                <a href="/file/${request.filename!}" style="display: block;">
                    <object width="300" height="350" type="application/pdf"
                            data="/file/${request.filename!}?#zoom=35.1&scrollbar=0&toolbar=0&navpanes=0">
                    </object>
                </a>
            </div>

            <br/>
            <ul class="list-group list-group-flush">
                 <#list checks as check>
                     <li class="list-group-item">
                         <div class="ml-2">
                             <label for="${check}" class="form-check-label"
                                    <#if (user.role=="VERIF" && (check=="CHECK" || check=="PAID")) ||
                                    (user.role=="ADMIN" && check=="VERIFIED")>
                                    onclick='window.event.returnValue=false'</#if> >
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

            <br/>
            <div class="form-group">
                <div class="row">
                    <div class="col-sm">
                        <label for="message">Сообщение для клиента</label>
                    </div>
                    <div class="col-sm text-right">
                        <input class="btn btn-secondary-info btn-sm" value="Очистить" type="reset"
                               onclick="document.getElementById('message').value='';">
                    </div>
                </div>
                <textarea name="message" class="textfield form-control" id="message" rows="2" value=""
                          placeholder="Введите сообщение для клиента">${request.message!}</textarea>
            </div>
        </div>
        <input type="hidden" value="${request.id}" name="requestId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-secondary-info btn-block">Сохранить</button>
    </div>
</form>
</@c.page>