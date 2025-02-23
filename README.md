# Download-TWSE-and-TSMC-daily-price
下載上市公司名單和TSMC股價並存入資料庫


流程
上市公司名單
1. 手動下載上市公司名單  https://mopsfin.twse.com.tw/opendata/t187ap03_L.csv
2. parse csv檔,取出公司名稱,代號
3. 把公司名稱和代號組合成 sql query(insert) ==>  儲存成sql檔
4. MySql 命令列中執行sql檔案,data寫入table

TSMC股價
1. 證交所網頁上取得TSMC 每日開高低收html網頁  https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY?date=20230101&stockNo=2330&response=html
   本次資料範圍 2023-2024
2. parse html ==> 取得 日期 開 高 低 收 
3. 2023 2024 日期 開 高 低 收 組合成 sql query(insert) ==>  儲存成sql檔
4. MySql 命令列中執行sql檔案,data寫入table


