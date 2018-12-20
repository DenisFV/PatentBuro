<#import "parts/common.ftl" as c>

<@c.page>
    Список пользователей
    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Роль</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <#if user.getUsername()!="admin">
                    <td><a href="/user/${user.id}">Изменить</a></td>
                </#if>
            </tr>
            <#else>
            </#list>
        </tbody>
    </table>
</@c.page>
