// 공통적인기능 따로빼놔서 
let backURL = 'http://192.168.0.20:8888/myback/'
let frontURL = 'http://192.168.0.20:5500/html/'
//$(() => {
//--로그인상태의 메뉴들 보여주기 함수 START--
function showMenuAtLogined() {
    $('header>nav>ul>li.login').hide()
    $('header>nav>ul>li.signup').hide()
    $('header>nav>ul>li.logout').show();
    $('header>nav>ul>li.productlist').show();
}
//--로그인상태의 메뉴들 보여주기 함수 END--

//--로그아웃상태의 메뉴들 보여주기 함수 START--
function showMenuAtLogouted() {
    $('header>nav>ul>li.login').show()
    $('header>nav>ul>li.signup').show()
    $('header>nav>ul>li.logout').hide();
    $('header>nav>ul>li.productlist').show();
}

//--현재 로그인상태인지 로그아웃상태인가를 요청하는 함수 START--
function checkLogined() {
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL + 'customer/checklogined',
        success: function (responseObj) {
            showMenuAtLogined();
        }, error: function (xhr) {
            showMenuAtLogouted();
        }
    });
}
//--현재 로그인상태인지 로그아웃상태인가를 요청하는 함수 END--

//--5초간격으로 로그인여부확인하기 함수 START--    
function checkIntervalLogined() {
    checkLogined();
    window.setInterval(checkLogined, 10 * 1000);
}
//--5초간격으로 로그인여부확인하기 함수 END--



$(() => {//이것은 돔 트리가 완성 되었을때만 객체를 찾아갈 수 있다. $(()=>{}) 이걸 써줌 그래서!위에!
    //--메뉴가 클릭되었을 때 할 일 START--
    $('header>nav>ul>li').click((e) => {
        $('header>nav>ul>li').css('background-color', '#fff').css('color', '#000')

        $(e.target).css('background-color', '#2C2A29').css('color', '#fff')
        let menu = $(e.target).attr('class')
        switch (menu) {
            case 'login':
                $('section').load('./login.html')
                break;
            case 'signup':
                $('section').load('./signup.html')
                break;
            case 'logout': //로드함수쓰는거 아님
                alert("로그아웃클릭됨")
                $.ajax({
                    xhrFields: {
                        withCredentials: true
                    },
                    url: backURL + 'customer/logout',

                    success: function () { //응답받은 내용이 없기때문에 매개변수e 안씀
                        //location.href = frontURL
                        //json으로 응답을 받으려면 return=""하면X 문자열이있어야되는데
                        //그래서 컨트롤러단에
                        alert('로그아웃되었습니다')
                        showMenuAtLogouted()
                    }
                })
                break;
            case 'productlist':
                //$.ajax
                $('section').load('./productlist.html')
                break;
            case 'cartlist':
                $('section').load('./cartlist.html')
                break;
            case 'orderlist':
                $('section').load('./orderlist.html')
                break;
            case 'boardlist':
                location.href='./boardlist.html'
                break;
        }
        return false
    })
    //--메뉴가 클릭되었을 때 할 일 END--

    //--로고가 클릭되었을 때 할 일 START--
    $('header>div.img').click(() => {
        location.href = frontURL
    })
    //--로고가 클릭되었을 때 할 일 END--

})