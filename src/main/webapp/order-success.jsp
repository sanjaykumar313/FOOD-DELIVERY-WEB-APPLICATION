<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%

String orderId =
request.getParameter("orderId");

%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Order Success</title>

<link rel="preconnect"
href="https://fonts.googleapis.com">

<link rel="preconnect"
href="https://fonts.gstatic.com"
crossorigin>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
rel="stylesheet">

<style>

*{

    margin: 0;
    padding: 0;

    box-sizing: border-box;

    font-family: 'Poppins', sans-serif;
}

body{

    background:
    linear-gradient(to right,
    #fff8f0,
    #fff3e6);

    height: 100vh;

    display: flex;

    justify-content: center;

    align-items: center;
}

/* SUCCESS BOX */

.success-box{

    background-color: white;

    width: 42%;

    padding: 50px;

    border-radius: 28px;

    text-align: center;

    box-shadow:
    0 8px 28px rgba(0,0,0,0.08);

    animation: popUp 0.5s ease;
}

/* POPUP ANIMATION */

@keyframes popUp{

    from{

        transform: scale(0.8);
        opacity: 0;
    }

    to{

        transform: scale(1);
        opacity: 1;
    }
}

/* SUCCESS ICON */

.success-icon{

    font-size: 80px;

    margin-bottom: 20px;
}

/* TITLE */

.success-title{

    font-size: 38px;

    font-weight: 700;

    color: #282c3f;

    margin-bottom: 15px;
}

/* SUBTITLE */

.success-subtitle{

    font-size: 20px;

    color: #686b78;

    margin-bottom: 35px;

    line-height: 1.6;
}

/* DELIVERY INFO */

.delivery-info{

    background-color: #fff7ef;

    padding: 18px;

    border-radius: 16px;

    margin-bottom: 35px;

    color: #fc8019;

    font-weight: 600;

    font-size: 17px;
}

/* BUTTONS */

.button-group{

    display: flex;

    justify-content: center;

    gap: 20px;
}

.action-btn{

    text-decoration: none;

    padding: 14px 28px;

    border-radius: 14px;

    font-weight: 600;

    transition: 0.3s ease;
}

/* HOME BUTTON */

.home-btn{

    background-color: #fc8019;

    color: white;
}

.home-btn:hover{

    background-color: #e46f10;
}

/* TRACK BUTTON */

.track-btn{

    background-color: #48c479;

    color: white;
}

.track-btn:hover{

    background-color: #34a862;
}

</style>

</head>

<body>

<div class="success-box">

    <!-- SUCCESS ICON -->

    <div class="success-icon">

        ✅

    </div>

    <!-- TITLE -->

    <h1 class="success-title">

        Order Placed Successfully!

    </h1>

    <!-- SUBTITLE -->

    <p class="success-subtitle">

        Your delicious food is being prepared 🍔<br>

        Thank you for ordering with us!

    </p>

    <!-- DELIVERY INFO -->

    <div class="delivery-info">

        🚚 Estimated delivery:
        20 - 30 mins

    </div>

    <!-- BUTTONS -->

    <div class="button-group">

        <!-- HOME BUTTON -->

        <a href="home.jsp"
           class="action-btn home-btn">

            Back To Home

        </a>

        <!-- TRACK ORDER BUTTON -->

        <a href="track-order.jsp?orderId=<%= orderId %>"
           class="action-btn track-btn">

            Track Order

        </a>

    </div>

</div>

</body>

</html>