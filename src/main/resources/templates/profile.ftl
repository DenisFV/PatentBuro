<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${user.username}</h5>
    <form action="/user/profile" method="post">


        <div class="form-group row">
            <label for="username" class="col-sm-1 col-form-label">Логин</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="username" name="username" placeholder="Логин"
                       <#if user.getUsername()=="admin">disabled</#if> value="${user.getUsername()}">
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-1 col-form-label">Пароль</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="password" name="password"
                       placeholder="Пароль" value="${user.getPassword()}">
            </div>
        </div>
        <input type="hidden" value="${user.getId()}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-secondary">Сохранить</button>
    </form>
</@c.page>