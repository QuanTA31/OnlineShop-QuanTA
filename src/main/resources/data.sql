-- =====================================================================
-- Du lieu mau cho OnlineShop (SWD392)
-- File nay chay moi lan khoi dong (spring.sql.init.mode=always) nen moi
-- lenh INSERT deu co guard de khong tao ban ghi trung.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. SETTINGS - du lieu cau hinh trung tam (muc 3.a cua requirements)
-- ---------------------------------------------------------------------
INSERT INTO settings (type, value, display_order, description, status) VALUES
    ('PRODUCT_CATEGORY', 'Thoi trang nam',    1, 'Quan ao, phu kien nam',   'ACTIVE'),
    ('PRODUCT_CATEGORY', 'Thoi trang nu',     2, 'Quan ao, phu kien nu',    'ACTIVE'),
    ('PRODUCT_CATEGORY', 'Do dien tu',        3, 'Thiet bi dien tu',        'ACTIVE'),
    ('PRODUCT_CATEGORY', 'Do gia dung',       4, 'Dung cu gia dinh',        'ACTIVE'),
    ('PRODUCT_CATEGORY', 'Sach',              5, 'Sach va van phong pham',  'INACTIVE'),
    ('POST_CATEGORY',    'Tin khuyen mai',    1, 'Bai viet ve khuyen mai',  'ACTIVE'),
    ('POST_CATEGORY',    'Huong dan mua hang',2, 'Huong dan cho khach',     'ACTIVE'),
    ('USER_ROLE',        'ADMIN',             1, 'Quan tri he thong',       'ACTIVE'),
    ('USER_ROLE',        'MARKETING',         2, 'Nhan vien marketing',     'ACTIVE'),
    ('USER_ROLE',        'SALE',              3, 'Nhan vien ban hang',      'ACTIVE'),
    ('USER_ROLE',        'SALE_MANAGER',      4, 'Quan ly ban hang',        'ACTIVE'),
    ('USER_ROLE',        'CUSTOMER',          5, 'Khach hang da dang ky',   'ACTIVE')
ON CONFLICT (type, value) DO NOTHING;

-- ---------------------------------------------------------------------
-- 2. USERS - mot vai tai khoan noi bo
-- ---------------------------------------------------------------------
INSERT INTO users (full_name, gender, email, mobile, address, password, role_id, status, verified)
SELECT 'Tran Anh Quan', 'MALE', 'marketing@shop.com', '0901000001', 'Ha Noi', NULL,
       (SELECT id FROM settings WHERE type = 'USER_ROLE' AND value = 'MARKETING'),
       'ACTIVE', TRUE
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (full_name, gender, email, mobile, address, password, role_id, status, verified)
SELECT 'Nguyen Thi Sale', 'FEMALE', 'sale@shop.com', '0901000002', 'Ha Noi', NULL,
       (SELECT id FROM settings WHERE type = 'USER_ROLE' AND value = 'SALE'),
       'ACTIVE', TRUE
ON CONFLICT (email) DO NOTHING;

-- ---------------------------------------------------------------------
-- 3. PRODUCTS
-- ---------------------------------------------------------------------
INSERT INTO products (title, slug, thumbnail, category_id, brief_info, description,
                      quantity, list_price, sale_price, featured, status,
                      created_date, updated_date)
SELECT v.title, v.slug, v.thumbnail,
       (SELECT id FROM settings WHERE type = 'PRODUCT_CATEGORY' AND value = v.category),
       v.brief_info, v.description, v.quantity, v.list_price, v.sale_price,
       v.featured, v.status, NOW(), NOW()
FROM (VALUES
    ('Ao so mi nam trang',    'ao-so-mi-nam-trang',    'https://picsum.photos/seed/p1/200', 'Thoi trang nam', 'Ao so mi cotton tron mau trang', 'Chat lieu cotton 100%, form regular, de phoi do.', 50, 350000, 299000, TRUE,  'ACTIVE'),
    ('Quan jean nam slimfit', 'quan-jean-nam-slimfit', 'https://picsum.photos/seed/p2/200', 'Thoi trang nam', 'Quan jean co gian form slim',   'Vai jean co gian nhe, phu hop di lam va di choi.', 30, 550000, 499000, FALSE, 'ACTIVE'),
    ('Ao khoac bomber nam',   'ao-khoac-bomber-nam',   'https://picsum.photos/seed/p3/200', 'Thoi trang nam', 'Ao khoac bomber mua thu',       'Lop lot du, chong gio nhe, 3 mau co ban.',        20, 750000, NULL,   FALSE, 'INACTIVE'),
    ('Vay hoa mua he',        'vay-hoa-mua-he',        'https://picsum.photos/seed/p4/200', 'Thoi trang nu',  'Vay hoa dang xoe nhe nhang',    'Chat lieu voan mem, hoa tiet hoa nho.',           40, 450000, 420000, TRUE,  'ACTIVE'),
    ('Ao len nu co lo',       'ao-len-nu-co-lo',       'https://picsum.photos/seed/p5/200', 'Thoi trang nu',  'Ao len mong giu am',            'Len mem khong ngua, co nhieu mau.',               25, 390000, NULL,   FALSE, 'ACTIVE'),
    ('Tui xach nu da PU',     'tui-xach-nu-da-pu',     'https://picsum.photos/seed/p6/200', 'Thoi trang nu',  'Tui xach cong so',              'Da PU cao cap, nhieu ngan, khoa kim loai.',       15, 650000, 590000, FALSE, 'ACTIVE'),
    ('Tai nghe bluetooth',    'tai-nghe-bluetooth',    'https://picsum.photos/seed/p7/200', 'Do dien tu',     'Tai nghe khong day pin 20h',    'Bluetooth 5.3, chong on chu dong, hop sac nhanh.',60, 890000, 790000, TRUE,  'ACTIVE'),
    ('Chuot khong day',       'chuot-khong-day',       'https://picsum.photos/seed/p8/200', 'Do dien tu',     'Chuot quang khong day im',      'Click im, pin AA dung 12 thang.',                 80, 250000, 199000, FALSE, 'ACTIVE'),
    ('Ban phim co',           'ban-phim-co',           'https://picsum.photos/seed/p9/200', 'Do dien tu',     'Ban phim co switch red',        'Layout 87 phim, den RGB, keycap PBT.',            12,1250000,1150000, TRUE,  'ACTIVE'),
    ('Noi chien khong dau',   'noi-chien-khong-dau',   'https://picsum.photos/seed/p10/200','Do gia dung',    'Noi chien 4.5 lit',             'Cong suat 1500W, 8 che do nau tu dong.',          18,1890000,1690000, FALSE, 'ACTIVE'),
    ('Binh giu nhiet 500ml',  'binh-giu-nhiet-500ml',  'https://picsum.photos/seed/p11/200','Do gia dung',    'Binh inox giu nhiet 12h',       'Inox 304, nap chong tran, 4 mau.',                70, 290000, 250000, FALSE, 'ACTIVE'),
    ('Bo dao nha bep 6 mon',  'bo-dao-nha-bep-6-mon',  'https://picsum.photos/seed/p12/200','Do gia dung',    'Bo dao kem gia go',             'Thep khong gi, can go tu nhien.',                  9, 980000, NULL,   FALSE, 'INACTIVE')
) AS v(title, slug, thumbnail, category, brief_info, description,
       quantity, list_price, sale_price, featured, status)
ON CONFLICT (slug) DO NOTHING;

-- ---------------------------------------------------------------------
-- 4. CUSTOMERS / CONTACTS
-- ---------------------------------------------------------------------
INSERT INTO customers (full_name, gender, email, mobile, address, status, created_date, updated_date)
SELECT v.full_name, v.gender, v.email, v.mobile, v.address, v.status, NOW(), NOW()
FROM (VALUES
    ('Le Minh Hoang',  'MALE',   'hoang.le@gmail.com',   '0912000001', 'So 1 Cau Giay, Ha Noi',      'CUSTOMER'),
    ('Pham Thu Trang', 'FEMALE', 'trang.pham@gmail.com', '0912000002', 'So 20 Le Loi, Da Nang',      'CUSTOMER'),
    ('Nguyen Van Binh','MALE',   'binh.nguyen@gmail.com','0912000003', 'So 5 Tran Hung Dao, Hue',    'POTENTIAL'),
    ('Do Thi Lan',     'FEMALE', 'lan.do@gmail.com',     '0912000004', 'So 88 Nguyen Trai, TP HCM',  'POTENTIAL'),
    ('Vu Quoc Dat',    'MALE',   'dat.vu@gmail.com',     '0912000005', 'So 12 Hai Ba Trung, Ha Noi', 'CONTACT'),
    ('Hoang Mai Anh',  'FEMALE', 'anh.hoang@gmail.com',  '0912000006', 'So 45 Quang Trung, Hai Phong','CONTACT'),
    ('Trinh Cong Son', 'MALE',   'son.trinh@gmail.com',  '0912000007', 'So 9 Ly Thuong Kiet, Can Tho','CONTACT'),
    ('Bui Thi Ngoc',   'FEMALE', 'ngoc.bui@gmail.com',   '0912000008', 'So 33 Hung Vuong, Nha Trang','CONTACT')
) AS v(full_name, gender, email, mobile, address, status)
ON CONFLICT (email) DO NOTHING;

-- ---------------------------------------------------------------------
-- 5. FEEDBACKS
-- Bang nay khong co rang buoc unique nen dung NOT EXISTS de chan trung.
-- ---------------------------------------------------------------------
INSERT INTO feedbacks (customer_id, product_id, rated_star, content, status, created_date)
SELECT (SELECT id FROM customers WHERE email = v.customer_email),
       (SELECT id FROM products  WHERE slug  = v.product_slug),
       v.rated_star, v.content, v.status, NOW()
FROM (VALUES
    ('hoang.le@gmail.com',    'tai-nghe-bluetooth',    5, 'Am thanh rat tot, pin trau dung duoc ca ngay.',        'APPROVED'),
    ('trang.pham@gmail.com',  'vay-hoa-mua-he',        4, 'Vay dep, vai mat nhung hoi dai so voi mo ta.',         'APPROVED'),
    ('binh.nguyen@gmail.com', 'ban-phim-co',           5, 'Go rat da tay, den dep, dang tien.',                   'APPROVED'),
    ('lan.do@gmail.com',      'noi-chien-khong-dau',   3, 'Nau ngon nhung tieng quat hoi to.',                    'PENDING'),
    ('dat.vu@gmail.com',      'chuot-khong-day',       4, 'Dung on dinh, click im nhu quang cao.',                'PENDING'),
    ('anh.hoang@gmail.com',   'ao-so-mi-nam-trang',    2, 'Ao bi nhan nhieu sau khi giat lan dau.',               'PENDING'),
    ('son.trinh@gmail.com',   'binh-giu-nhiet-500ml',  5, 'Giu nong qua 10 tieng, rat hai long.',                 'APPROVED'),
    ('ngoc.bui@gmail.com',    'tui-xach-nu-da-pu',     1, 'Hang khong giong anh, duong may xau.',                 'REJECTED'),
    ('hoang.le@gmail.com',    'quan-jean-nam-slimfit', 4, 'Form chuan, mac thoai mai.',                           'APPROVED'),
    ('trang.pham@gmail.com',  NULL,                    5, 'Giao hang nhanh, nhan vien tu van nhiet tinh.',        'PENDING')
) AS v(customer_email, product_slug, rated_star, content, status)
WHERE NOT EXISTS (SELECT 1 FROM feedbacks);

-- ---------------------------------------------------------------------
-- 6. ORDERS + ORDER_DETAILS
-- Can thiet de customers.status trong seed khop voi quy tac he thong tu
-- sinh (co don SUCCESS -> CUSTOMER, co don bat ky -> POTENTIAL).
-- Neu thieu phan nay, lan sua customer dau tien se bi ha ve CONTACT.
-- ---------------------------------------------------------------------
INSERT INTO orders (order_date, customer_id, assigned_sale_id, total_cost, status,
                    receiver_full_name, receiver_gender, receiver_email,
                    receiver_mobile, receiver_address, notes)
SELECT NOW() - (v.days_ago || ' days')::interval,
       (SELECT id FROM customers WHERE email = v.customer_email),
       (SELECT id FROM users WHERE email = 'sale@shop.com'),
       v.total_cost, v.status,
       c.full_name, c.gender, c.email, c.mobile, c.address, NULL
FROM (VALUES
    ('hoang.le@gmail.com',    790000,  'SUCCESS',   5),
    ('trang.pham@gmail.com',  420000,  'SUCCESS',   4),
    ('binh.nguyen@gmail.com', 1150000, 'SUBMITTED', 2),
    ('lan.do@gmail.com',      1690000, 'SUBMITTED', 1)
) AS v(customer_email, total_cost, status, days_ago)
JOIN customers c ON c.email = v.customer_email
WHERE NOT EXISTS (SELECT 1 FROM orders);

INSERT INTO order_details (order_id, product_id, unit_price, quantity, total_cost)
SELECT o.id,
       (SELECT id FROM products WHERE slug = v.product_slug),
       v.unit_price, v.quantity, v.unit_price * v.quantity
FROM (VALUES
    ('hoang.le@gmail.com',    'tai-nghe-bluetooth',   790000,  1),
    ('trang.pham@gmail.com',  'vay-hoa-mua-he',       420000,  1),
    ('binh.nguyen@gmail.com', 'ban-phim-co',          1150000, 1),
    ('lan.do@gmail.com',      'noi-chien-khong-dau',  1690000, 1)
) AS v(customer_email, product_slug, unit_price, quantity)
JOIN customers c ON c.email = v.customer_email
JOIN orders o ON o.customer_id = c.id
WHERE NOT EXISTS (SELECT 1 FROM order_details);
