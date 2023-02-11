$(()=>{
    //--장바구니 목록 보여주기 START--
    let $origin = $('div.cartlist>div.item').first()
    let $cartlist = $('div.cartlist')
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url: backURL + 'cart/list',
        success:function(jsonObj){
            $(jsonObj).each((index, item)=>{
                console.log(item)
                let $copy = $origin.clone()
                $copy.find('ul>li.prodNo').html(item.prodNo)
                $copy.find('ul>li.quantity').html(item.quantity)
                $cartlist.append($copy)
            })
            // $origin.hide()
        },error(xhr){
            if(xhr.status == 400){
                alert('장바구니가 비었습니다')
            }
        }
    })
    //--장바구니 목록 보여주기 END--

    //--주문하기 버튼 클릭 START--
    $('div.order>button').click(()=>{
            $.ajax({
                xhrFields: {
                    withCredentials: true
                },
                url : backURL + 'order/add',
                success: function(jsonObj){
                        alert('주문성공')                    
                        $('header > nav > ul > li.orderlist').click()
                    
                },error: function(xhr){
                    alert('주문실패:' + xhr.responseText)
                }
            })
            return false
        }
        
    )
    //--주문하기 버튼 클릭 END--
})