$(()=>{
    //--상품정보보여주기 START--
    let url = backURL + 'product/info'
    let data = location.search.substring(1) //prodNo=C0001
    $.ajax({
        url: url,
        method: 'get',
        data:data,
        success: function(jsonObj){
            console.log(jsonObj)
           $('div.img>img').attr('src', '../images/' + jsonObj.prodNo+'.jpg') 
           $('div.prodname>h4').html(jsonObj.prodName)
           $('div.proddetail').html(jsonObj.prodDetail)
           $('div.prodno').html('상품번호:' + jsonObj.prodNo) 
           $('div.prodprice').html('가격:' + jsonObj.prodPrice+'원')
           $('div.prodmfdt').html('제조일자:' + jsonObj.prodMfDt)
        },
        error: function(xhr){
            alert(xhr.status)
        }
    })
    //--상품정보보여주기 END--

    //--상품상세에서 메뉴클릭 START--
    $('header>nav>ul>li').click((e)=>{
        $('header>nav>ul>li').css('background-color', '#fff').css('color', '#000')

        $(e.target).css('background-color', '#2C2A29').css('color', '#fff')
        let menu = $(e.target).attr('class')
        switch(menu){
            case 'login':
                location.href=frontURL+'?menu=login'
                break;
            case 'signup':
                location.href=frontURL+'?menu=signup'
                break;
            case 'logout':
                location.href=frontURL+'?menu=logout'
                break;
            case 'productlist':
                //$.ajax
                //$('section').load('./productlist.html')
                location.href=frontURL+'?menu=productlist'
                break;
            case 'cartlist':
                //$.ajax
                //$('section').load('./productlist.html')
                location.href=frontURL+'?menu=cartlist'
                break;
        }
    })
  //--상품상세에서 메뉴클릭 END--

//--장바구니 넣기 버튼 클릭 START
$('div.cart>button').click(()=>{
    //상품번호 얻기
    let prodNo = location.search.substring(1).split("=")[1]
    let quantity = $('div.quantity>input[type=number]').val()
    let data = {
        prodNo : prodNo,
        quantity : quantity
    }
    let url = backURL + 'cart/put'
    $.ajax({
        xhrFields: {
            withCredentials: true
         },
        url: url,
        method: 'post',
        data: data,
        success: function(){
            $('div.choose').show()
        }
    })
})

//--장바구니 넣기 버튼 클릭 END


//--장바구니 보기 버튼 클릭 START
$('div.choose>button.cartlist').click(()=>{
    location.href=frontURL+'?menu=cartlist'
    return false
})
//--장바구니 보기 버튼 클릭 END

//--상품버튼 클릭 START
$('div.choose>button.productlist').click(()=>{
    location.href=frontURL+'?menu=productlist'
    return false
})  
//--상품버튼 클릭 END
})