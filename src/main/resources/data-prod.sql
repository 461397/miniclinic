-- 初始醫師資料 (包含你上週產生的密碼)
INSERT INTO doctor (doctor_id, name, department, specialty, password_hash) VALUES
    ('D001', '陳志明醫師', '家醫科', '一般內科、慢性病管理', '$2a$10$w8k2s.vQ.g4Xn8G6/H7T8.r3X1C9jK0uN5fD7B1mR2pQ4T6eL8yW'),
    ('D002', '林佩君醫師', '內科',   '心臟血管、高血壓', '$2a$10$T2r5Y.mQ.v8Xn4G6/H7T8.e3X1C9jK0uN5fD7B1mR2pQ4T6eL8yW'),
    ('D003', '王建華醫師', '復健科', '運動傷害、脊椎復健', '$2a$10$P5m8Y.zQ.c8Xn2G6/H7T8.f3X1C9jK0uN5fD7B1mR2pQ4T6eL8yW'),
    ('D004', '李美玲醫師', '小兒科', '兒童感冒、疫苗接種', '$2a$10$Q4u9Y.vQ.h8Xn3G6/H7T8.q3X1C9jK0uN5fD7B1mR2pQ4T6eL8yW'),
    ('D005', '張雅筑醫師', '身心科', '焦慮、失眠、情緒調適', '$2a$10$L7x4Y.bQ.a8Xn1G6/H7T8.x3X1C9jK0uN5fD7B1mR2pQ4T6eL8yW')
ON CONFLICT (doctor_id) DO NOTHING;

-- 初始病患資料
INSERT INTO patient (chart_no, name, gender, birth_date, phone) VALUES
    ('TEST00001', '測試病患甲', '男', '1985-03-15', '0912-345-678'),
    ('TEST00002', '王小明',     '男', '1990-07-22', '0923-456-789'),
    ('TEST00003', '李小華',     '女', '1988-11-30', '0934-567-890')
ON CONFLICT (chart_no) DO NOTHING;

-- 初始掛號資料
INSERT INTO appointment (appt_id, chart_no, doctor_id, appt_date, time_slot, status) VALUES
    (1, 'TEST00001', 'D001', '2026-05-01', 'AM', 'BOOKED'),
    (2, 'TEST00002', 'D002', '2026-05-01', 'AM', 'BOOKED'),
    (3, 'TEST00003', 'D003', '2026-05-02', 'PM', 'BOOKED')
ON CONFLICT (appt_id) DO NOTHING;