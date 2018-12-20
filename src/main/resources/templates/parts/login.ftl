<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div class="form-group row">
            <label for="username" class="col-sm-1 col-form-label">Логин</label>
            <div class="col-sm-5">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                       id="username" name="username" value="<#if user??>${user.username}</#if>" placeholder="Логин">
                <#if !isRegisterForm>
                    <script>
                        var url = window.location.href.toString()=="http://localhost:8080/login?error";
                        if(url) document.write("<span style= \"color:#dc3545; font-size:80%\" >Неверный логин или пароль</span>");
                    </script>
                <#else>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-1 col-form-label">Пароль</label>
            <div class="col-sm-5">
                <input type="text" class="form-control ${(passwordError??)?string('is-invalid','')}"
                       id="password" name="password"
                       value="<#if user??>${user.password}</#if>"
                       placeholder="Пароль">
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="form-group row">
                <label for="password2" class="col-sm-1 col-form-label"></label>
                <div class="col-sm-5">
                    <input type="text" class="form-control ${(password2Error??)?string('is-invalid','')}"
                           id="password2" name="password2" placeholder="Повтор пароля">
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <#if !isRegisterForm><a href="/registration">Регистрация</a></#if>
        <button type="submit" class="btn btn-secondary"><#if isRegisterForm>Регистрация<#else>Вход</#if></button>
    </form>
</#macro>

<#macro logout isActive>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#--<#if isActive>-->
            <button type="submit" class="btn btn-secondary"><#if isActive>Выход<#else>Вход</#if></button>
        <#--</#if>-->
    </form>
</#macro>