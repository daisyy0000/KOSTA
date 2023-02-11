$(()=>{
    checkIntervalLogined()
    //menu=productlist
    
    // location 프로퍼티는 현재 창에 표시된 문서의 URL
    // location.search를 이용하여 URL 물음표 뒤에 있는 파라미터들을 가져올 수 있다.  ?param=1234
    // substring(1): [1]번째부터 가져옴 param=1234
    let queryStr = location.search.substring(1)
    //arr 1234
    let arr = queryStr.split('=')
    if(arr[0] == 'menu'){
        console.log('메뉴', arr[1])
        switch(arr[1]){
        case 'login':
            $('header>nav>ul>li.login').click()
            break;
        case 'signup':
            $('header>nav>ul>li.signup').click()
            break;
        case 'logout':
            $('header>nav>ul>li.logout').click()
            break;
        case 'productlist':
            $('header>nav>ul>li.productlist').click()
            break;
        case 'cartlist':
            $('header>nav>ul>li.cartlist').click()
            break;     
        case 'orderlist':
            $('header>nav>ul>li.orderlist').click()
            break;     
         case 'boardlist':
            location.href='./boardlist.html'
            break;
         }
    }
 })