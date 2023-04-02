<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Upload</h2>
<section class="my-5">
    <div class="container">
        <div class="row">
            <div class="col-md-8 mx-auto">
                <security:authorize access="{hasRole('ADMIN') or hasRole('USER')}" >
                    <form method=POST enctype=multipart/form-data>
                        <input type="file" name="image" accept="image/*" class="form-control-file">
                        <button type="submit" class="btn btn-primary">Upload image</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </security:authorize>
            </div>
        </div>
    </div>
</section>
</body>
</html>
