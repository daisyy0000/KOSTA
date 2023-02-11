$(()=>{
    let url = backURL + 'order/list'
    let $origin = $('div.orderlist>table>tbody>tr').first() //원본
    let $parent = $origin.parent() //$('div.orderlist>table>tbody')
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        url : url,
        success: function(jsonObj){
            // if(jsonObj.status < 1){
            //     $origin.hide()
            //     alert(jsonObj.msg)
            //     return
            // }
            let $originLineDiv = $('div.orderlist>table tr>td.lines>div').first()
            $(jsonObj).each((index, orderInfo)=>{  
                let orderNo = orderInfo.orderNo
                let orderDt = orderInfo.orderDt
                let lines = orderInfo.lines
                
                let lineSize = lines.length //주문상세들
                let $copy = $origin.clone() //복제본
                $copy.find('td.orderNo').html(orderNo)
                $copy.find('td.orderDt').html(orderDt)
                let $parentLine = $copy.find('td.lines')
                $(orderInfo.lines).each((index, orderLine)=>{
                    let $copyLineDiv = $originLineDiv.clone()
                    $copyLineDiv.find('li.prodNo').html(orderLine.orderP.prodNo)
                    $copyLineDiv.find('li.prodName').html(orderLine.orderP.prodName)
                    $copyLineDiv.find('li.prodPrice').html(orderLine.orderP.prodPrice)
                    $copyLineDiv.find('li.orderQuantity').html(orderLine.orderQuantity)
                    $parentLine.append($copyLineDiv)
                }) 
              
                $parent.append($copy)
            })
            $origin.hide()
        }, error: function(xhr){
            alert(xhr.status  + ": " + xhr.responseText)
        }
    })
})