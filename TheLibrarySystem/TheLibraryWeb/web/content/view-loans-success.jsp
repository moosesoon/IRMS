<!DOCTYPE html>
<%
    if(session.getAttribute("id") == null || session.getAttribute("name") == null ||
                session.getAttribute("type") == null){
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

%>
<html>
<head>
<title>View Loans - The Library System</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<link rel='stylesheet' type='text/css' href='http://cdn1.editmysite.com/editor/libraries/fancybox/fancybox.css?1363370473' />
<link rel='stylesheet' href='http://cdn1.editmysite.com/editor/images/common/common-v2.css?buildTime=1363370473' type='text/css' />
<link rel='stylesheet' type='text/css' href='../files/main_style.css?1363504916' title='wsite-theme-css' />
<style type='text/css'>
.wsite-elements div.paragraph, .wsite-elements p, .wsite-elements .product-block .product-title, .wsite-elements .product-description, .wsite-elements .wsite-form-field label, .wsite-elements .wsite-form-field label, #wsite-content div.paragraph, #wsite-content p, #wsite-content .product-block .product-title, #wsite-content .product-description, #wsite-content .wsite-form-field label, #wsite-content .wsite-form-field label, .blog-sidebar div.paragraph, .blog-sidebar p, .blog-sidebar .wsite-form-field label, .blog-sidebar .wsite-form-field label {}
#wsite-content div.paragraph, #wsite-content p, #wsite-content .product-block .product-title, #wsite-content .product-description, #wsite-content .wsite-form-field label, #wsite-content .wsite-form-field label, .blog-sidebar div.paragraph, .blog-sidebar p, .blog-sidebar .wsite-form-field label, .blog-sidebar .wsite-form-field label {}
.wsite-elements h2, .wsite-elements .product-long .product-title, .wsite-elements .product-large .product-title, .wsite-elements .product-small .product-title, #wsite-content h2, #wsite-content .product-long .product-title, #wsite-content .product-large .product-title, #wsite-content .product-small .product-title, .blog-sidebar h2 {}
#wsite-content h2, #wsite-content .product-long .product-title, #wsite-content .product-large .product-title, #wsite-content .product-small .product-title, .blog-sidebar h2 {}
#wsite-title {}
</style>
<script type='text/javascript'><!--
var STATIC_BASE = 'http://cdn1.editmysite.com/';
var STYLE_PREFIX = 'wsite';

function submitID1(){
    document.viewloans1.submit();
}
function submitID2(){
    document.viewloans2.submit();
}
function submitRID(){
    document.viewreserve.submit();
}
//-->
</script>
<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>
<script type='text/javascript' src='http://cdn1.editmysite.com/editor/libraries/jquery_effects.js?1363370473'></script>
<script type='text/javascript' src='http://cdn1.editmysite.com/editor/libraries/jquery.animate.js?1363370473'></script>
<script type='text/javascript' src='http://cdn1.editmysite.com/editor/libraries/fancybox/fancybox.min.js?1363370473'></script>
<script type='text/javascript' src='http://cdn1.editmysite.com/editor/images/common/utilities-jq.js?1363370473'></script>
<script type='text/javascript' src='http://cdn1.editmysite.com/editor/libraries/flyout_menus_jq.js?1363370473'></script>
<script type='text/javascript'><!--
var IS_ARCHIVE=1;
(function(jQuery){
function initFlyouts(){initPublishedFlyoutMenus([{"id":"164051370946620007","title":"Home","url":"../index.jsp"},{"id":"830073776590146514","title":"Books Menu","url":"view-books.jsp"},{"id":"960687116878728986","title":"Loans Menu","url":"view-loans.jsp"},{"id":"403567283975139308","title":"Reservation Menu","url":"view-reservation.jsp"}],'830073776590146514',"<li class='wsite-nav-more'><a href='#'>more...<\/a><\/li>",'active',false)}
if (jQuery) {
if (jQuery.browser.msie) window.onload = initFlyouts;
else jQuery(initFlyouts)
}else{
if (Prototype.Browser.IE) window.onload = initFlyouts;
else document.observe('dom:loaded', initFlyouts);
}
})(window._W && _W.jQuery)
//-->
</script>
</head>
<body class='wsite-theme-light no-header-page wsite-page-view-loans'>
<div id="container">
	<div id="header">
	<div id="header-top">
	<div id="header-right">
	<table id="header-right-table">
		<tbody>
			<tr>
				<td>
				<div class="search">
				</div>
				<div class="social">
				<div style="text-align:left;">
                                <div style="height:0px;overflow:hidden"></div>

                                <span class="wsite-social wsite-social-default">
                                <a class='wsite-social-item wsite-social-facebook' href='http://localhost/fakedb' target='_blank'>
                                <span class='wsite-social-item-inner'></span></a>
                                <a class='wsite-social-item wsite-social-twitter' href='http://localhost/faketwitter' target='_blank'>
                                <span class='wsite-social-item-inner'></span></a>
                                <a class='wsite-social-item wsite-social-linkedin' href='http://localhost/fakelinkedin' target='_blank'>
                                <span class='wsite-social-item-inner'></span></a>
                                <a class='wsite-social-item wsite-social-mail' href='mailto:localhost@fakemail.com' target='_blank'>
                                <span class='wsite-social-item-inner'></span></a></span>
                                <div style="height:0px;overflow:hidden"></div></div>
				</div>
				<div class="phone-number">
				<span class='wsite-text'>Contact Us!</span>
				</div>
				<div class="clear"></div>
                                </td>
                        </tr>
                </tbody>
	</table>
	</div>
	<div class="clear"></div>
	</div>
	<div id="bot-section">
	<div id="header-bot">
	<div id="header-content">
	<div id="title">
	<span class='wsite-logo'>
            <table style='height:32px'>
                <tr>
                    <td>
                        <a href=''>
                        <span id="wsite-title">The Library System</span>
                        </a>
                    </td>
                </tr>
            </table>
        </span>
	</div>
	<div id="navigation">
	<ul>
            <li id='pg164051370946620007'>
                <a href='home'>Home</a>
            </li>
            <li id='pg830073776590146514'>
                <a href='view-books'>Books Menu</a>
            </li>
            <li id='active'>
                <form name="viewloans1" action="view-loans" method="POST">
                    <input type="hidden" name="mid" value="<%= session.getAttribute("id")%>" />
                    <a href="javascript: submitID1()">Loan Menu</a>
                </form>
            </li><li id='pg403567283975139308'>
                <form name="viewreserve" action="view-reservation" method="POST">
                    <input type="hidden" name="mid" value="<%= session.getAttribute("id")%>" />
                <a href="javascript: submitRID()">Reservation Menu</a>
                </form>
            </li>
        </ul>
	</div>
	<img src="../files/theme/pencil.png" alt="" id="pencil"/>
	</div>
	</div>
	</div>
	</div>
	<div id="main">
		<div id="banner">
			<div class="wsite-header"></div>
		</div>
		<div id="content">
		<div id='wsite-content' class='wsite-elements wsite-not-footer'>
                    <div class="paragraph" style="text-align:center">
                        Update success! Click
                        <form name="viewloans2" action="view-loans" method="POST">
                            <input type="hidden" name="mid" value="<%= session.getAttribute("id")%>" />
                            <a href="javascript: submitID2()">HERE</a>
                             to go back!
                        </form>
                    </div>
                </div>
		<div class="clear"></div>
		</div>
	</div>
	<div id="footer" style="text-align:center">
		Created by Stanley. Web Design credit goes to Weebly
	<div class="clear"></div>
</div>

</body>
</html>