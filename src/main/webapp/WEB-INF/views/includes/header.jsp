<%@ page import="za.ac.cput.hospital.model.User" %><%
User currentUser=(User)session.getAttribute("user"); String ctx=request.getContextPath();
String flash=(String)session.getAttribute("flash"),flashType=(String)session.getAttribute("flashType");session.removeAttribute("flash");session.removeAttribute("flashType");
%><!doctype html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Ubuntu Health</title><link rel="stylesheet" href="<%=ctx%>/assets/style.css"><script defer src="<%=ctx%>/assets/app.js"></script></head><body>
<header class="topbar"><a class="brand" href="<%=ctx%>/dashboard">Ubuntu Health</a><button class="nav-toggle" aria-label="Toggle navigation">Menu</button><nav>
<a href="<%=ctx%>/dashboard">Dashboard</a><a href="<%=ctx%>/appointments/">Appointments</a>
<%if(currentUser!=null&&currentUser.role().equals("PATIENT")){%><a href="<%=ctx%>/symptoms">Symptom checker</a><%}%>
<%if(currentUser!=null&&(currentUser.role().equals("ADMIN")||currentUser.role().equals("SYSTEM_ADMIN"))){%><a href="<%=ctx%>/users">Users</a><%}%>
<a href="<%=ctx%>/logout">Sign out</a></nav></header><main class="container">
<%if(flash!=null){%><div class="alert <%= "error".equals(flashType)?"alert-error":"alert-success" %>" role="status"><%=flash%></div><%}%>
