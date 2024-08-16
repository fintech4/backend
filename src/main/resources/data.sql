INSERT INTO stock_metadata (stock_code, stock_name, market)
VALUES ('095570', 'AJ네트웍스', 'KOSPI'),
       ('006840', 'AK홀딩스', 'KOSPI'),
       ('027410', 'BGF', 'KOSPI'),
       ('282330', 'BGF리테일', 'KOSPI'),
       ('138930', 'BNK금융지주', 'KOSPI'),
       ('001460', 'BYC', 'KOSPI'),
       ('001465', 'BYC우', 'KOSPI'),
       ('001040', 'CJ', 'KOSPI'),
       ('079160', 'CJ CGV', 'KOSPI'),
       ('00104K', 'CJ4우(전환)', 'KOSPI'),
       ('000120', 'CJ대한통운', 'KOSPI'),
       ('011150', 'CJ씨푸드', 'KOSPI'),
       ('011155', 'CJ씨푸드1우', 'KOSPI'),
       ('001045', 'CJ우', 'KOSPI'),
       ('097950', 'CJ제일제당', 'KOSPI'),
       ('097955', 'CJ제일제당 우', 'KOSPI'),
       ('000480', 'CR홀딩스', 'KOSPI'),
       ('000590', 'CS홀딩스', 'KOSPI');


INSERT INTO stock_history (stock_metadata_id, open_price, high_price, low_price, closing_price,
                           date)
VALUES (1, 10000, 11000, 9000, 10500, '2023-08-01'),  -- AJ네트웍스
       (1, 10500, 11500, 9500, 11000, '2023-08-02'),  -- AJ네트웍스
       (2, 20000, 21000, 19500, 20500, '2023-08-01'), -- AK홀딩스
       (2, 20500, 21500, 20000, 21000, '2023-08-02'), -- AK홀딩스
       (3, 15000, 15500, 14500, 15200, '2023-08-01'), -- BGF
       (3, 15200, 15800, 15000, 15700, '2023-08-02');
-- BGF

-- 초기 Account Asset 데이터
INSERT INTO account_asset (kakao_id, total_asset, deposit, total_holdings_value,
                           total_holdings_quantity, investment_yield, total_principal)
VALUES ('kakao123', 500000, 500000, 0, 0, 0, 0),
       ('kakao456', 500000, 500000, 0, 0, 0, 0);

-- 초기 Holding Individual Stock 데이터
-- INSERT INTO holding_individual_stock (stock_code, stock_name, average_purchase_price, current_price,
--                                       quantity, valuation, yield, account_asset_id)
-- VALUES ('095570', 'AJ네트웍스', 10000, 10500, 50, 525000, 5.0, 1), -- user1's holding
--        ('006840', 'AK홀딩스', 20000, 21000, 20, 420000, 5.0, 1),  -- user1's holding
--        ('027410', 'BGF', 15000, 15700, 30, 471000, 4.67, 2); -- user2's holding
