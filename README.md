# Coupon System Project
## Table of contents
* [General info](#general-info)
* [About the Project](#about)
* [Main entities](#main-entities)
* [Features](#The-system-was-built-with-some-Design-patterns-such-as)
* [The Project will be build in 3 phases](#The-Project-will-be-build-in-3-phases)
* [View](#view)

## General info
The Coupon System Project acts as a Micro Service, that shows an implementation which allows companies to CRUD coupons for their customers. then the Customers can purchase and use those Coupons, and get a discount in that company's "business".
	
## About the Project
+ A client-Server Coupon management system enables companies to generate coupons as part of their advertising and marketing campaigns.
+ The system also has registered customers. 
+ Customers can purchase coupons. 
+ Coupons are limited in quantity and validity. 
+ Customer is limited to one coupon of each type.
+ The system records the coupons purchased by each customer.
+ The system's revenues are from the purchase of coupons by customers and the creation and updating of new coupons by the companies.

## main entities(*)
+ Admin – can create, read, update, delete -> companies, customers.
+ Company – can create, read, update, delete coupons.
+ Customer – can check available coupons, purchase them and check list of purchased coupons.

## The system was built with some Design patterns such as
Singleton, Data Access Object - Dao, Factory, Facade, Thread + Connection Pool, Iterator etc.

## The project will be build in 3 phases
+ Phase 1 - Stand alone java Core application.
+ Phase 2 - Will add RestAPI Web Services and client side using Angular. (soon)
+ Phase 3 - Will change the project to Spring framework and microservices application. (soon) 

## For Phase 1:
+ The project base on MySQL Workbench Server Database.
+ running on Port 3306 , user: root , password: root.

## View
+ login as Admin ( Management concole , email: admin@admin.com , password: admin)
	
