

<nav class="navbar navbar-light navbar-expand-md navigation-clean-search navbar-fixed-top" id="navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="../main/index.php">USC Schedule Helper</a>
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav">
                <li class="nav-item"></li>
                <li class="nav-item dropdown"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#">Menu&nbsp;</a>
                    <div class="dropdown-menu"><a class="dropdown-item" href="../schedule/schedule.php">Scheduling</a><a class="dropdown-item" href="../social/social.php">Social</a><a class="dropdown-item" target="_blank" href="https://github.com/schedule-gurus">Project GitHub</a></div>
                </li>
            </ul>
        <form class="form-inline mr-auto" target="_self">
            <div class="form-group"><label for="search-field"></label></div>
        </form>
            <?php if(!isset($_SESSION["logged_in"]) || !$_SESSION["logged_in"]) : ?>
                <span class="navbar-text"><a class="login btn btn-dark action-button" role="button" href="../login-page/login.php">Log In</a></span>
                <span class="navbar-text"><a class="btn btn-light action-button" role="button" href="../login-page/register.php">Sign up</a></span>

            <?php else: ?>
                <!-- Todo: change this to say hi to persons first name -->
                <span class="navbar-text" id="hi"><a class="login btn btn-dark action-button" role="button" href="#">Hi <?php echo $_SESSION["name"]; ?>!</a></span>
                <a class="btn btn-light action-button" role="button" href="../login-page/logout.php">Log Out</a>

            <?php endif; ?>
    </div>
</div>
</nav>


        