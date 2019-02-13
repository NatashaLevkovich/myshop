$(document).ready(function () {

    SubmenuHide();

    $('.catalog').mouseout(function () {
        SubmenuHide();
    });

    $('.catalog').mouseover(function () {
        SubmenuShow();

    });

    $('.submenu').mouseover(function () {
        SubmenuShow();

    });
    $('.submenu').mouseout(function () {
        SubmenuHide();
    });
});

function SubmenuShow() {
    $('.submenu').show();
}

function SubmenuHide() {
    $('.submenu').hide();
}
