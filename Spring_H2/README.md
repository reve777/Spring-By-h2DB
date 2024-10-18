1. 測試呼叫查詢幣別對應表資料API，並顯示其內容。  
GET  http://localhost:8081/portfolio/api/forex/getOne/USD  
2. 測試呼叫新增幣別對應表資料API。  
http://localhost:8081/portfolio/api/forex/insertApiByCode?code=USD  

3. 測試呼叫更新幣別對應表資料API，並顯示其內容。  
PUT  http://localhost:8081/portfolio/api/forex/updateApi  

4. 測試呼叫刪除幣別對應表資料API。  
DELETE http://localhost:8081/portfolio/api/forex/delete/USD  
DELETE http://localhost:8081/portfolio/api/forex/deleteAll  

5. 測試呼叫coindesk API，並顯示其內容。  
GET  http://localhost:8081/portfolio/api/forex/getApiData  

6. 測試呼叫資料轉換的API，並顯示其內容。  
GET  http://localhost:8081/portfolio/api/forex/insertApi  


CODE 幣別 USD GBP EUR  
------------------------------------------------------------------------------------------------  
H2DB:  
Driver Class:org.h2.Driver  
JDBC URL:jdbc:h2:mem:study  
User Name:sa  
Password:  
http://localhost:8081/portfolio/h2-console/login.do?jsessionid=11d8578c82b7b6f0a9a5aceed273dc94  
