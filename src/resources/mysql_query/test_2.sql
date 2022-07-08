
SELECT Max(Price_Out-Price_In) FROM selles;
SELECT Max(Price_Out-Price_In) FROM selles;

SELECT brand FROM selles WHERE Price_Out-Price_In=(SELECT Max(Price_Out-Price_In) FROM selles);