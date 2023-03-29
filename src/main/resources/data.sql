INSERT INTO author(id, name, slug, photo, description) SELECT 1, 'name', '123123', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description' WHERE NOT EXISTS(SELECT id FROM author WHERE id = 1);
INSERT INTO users SELECT 1, '123123', to_date('1-9-2011', 'DD-MM-YYYY'), 0.00, 'danilcherep7@gmail.com' WHERE NOT EXISTS (SELECT id FROM users WHERE id = 1);
INSERT INTO roles(id, name) SELECT 1, 'ROLE_USER' WHERE NOT EXISTS(SELECT id FROM roles WHERE id = 1);
INSERT INTO user_roles(user_id, role_id) SELECT 1, 1 WHERE NOT EXISTS(SELECT user_id FROM user_roles WHERE user_id = 1);
INSERT INTO user_contact SELECT 1,1, 1, 1, '123123', 0, to_date('1-9-2011', 'DD-MM-YYYY'), 'danilcherep7@gmail.com' WHERE NOT EXISTS(SELECT id FROM user_contact WHERE id = 1);
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 1, 1, 'book-kzx-285', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 1) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-285');
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 2, 1, 'book-kzx-286', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 2) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-286');
INSERT INTO book(id, is_bestseller, slug, title, image, description, price, pub_date, discount) SELECT 3, 1, 'book-kzx-287', 'title', 'src/test/resources/uploads/820237033ad1c070e-2c2e-4365-be1c-edab5188a6b9.jpg', 'description', 100, to_date('01-09-2011', 'DD-MM-YYYY'), 100 WHERE NOT EXISTS(SELECT id FROM book WHERE id = 3) AND NOT EXISTS(SELECT slug FROM book WHERE slug = 'book-kzx-287');
INSERT INTO author(id, name, slug, photo, description) SELECT 1, 'name', 'book-svi-028', 'http://dummyimage.com/374x416.png/ff4444/ffffff', 'description' WHERE NOT EXISTS(SELECT id FROM author WHERE id = 1);
INSERT INTO book2author(id, book_id, authors_id, sort_index) SELECT 1, 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 1);
INSERT INTO book2author(id, book_id, authors_id, sort_index) SELECT 2, 2, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 2);
INSERT INTO book2author(id, book_id, authors_id, sort_index)
SELECT 3, 3, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2author WHERE id = 3);
INSERT INTO tag
SELECT 1, 'name', 'tag-121', 4, 0 WHERE NOT EXISTS(SELECT id FROM tag WHERE id = 1);
INSERT INTO book2tag
SELECT 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 1);
INSERT INTO book2tag
SELECT 2, 2, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 2);
INSERT INTO book2tag
SELECT 3, 3, 1 WHERE NOT EXISTS(SELECT id FROM book2tag WHERE id = 3);
INSERT INTO book_file
SELECT 1, '123', 1, 0.00, 'file.epub', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 1);
INSERT INTO file_download
SELECT 1, 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM file_download WHERE id = 1);
INSERT INTO book_file
SELECT 2, '1234', 2, 0.00, 'file.fb2', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 2);
INSERT INTO book_file
SELECT 3, '12345', 3, 0.00, 'file.pdf', 2 WHERE NOT EXISTS(SELECT id FROM book_file WHERE id = 3);
INSERT INTO file_download
SELECT 2, 1, 1, 1 WHERE NOT EXISTS(SELECT id FROM file_download WHERE id = 2);
insert into book2file (id, book_id, file_id)
values (1, 1, 1);
insert into book2file (id, book_id, file_id)
values (2, 2, 2);
insert into book2file (id, book_id, file_id)
values (3, 3, 3);
insert into book2file (id, book_id, file_id)
values (4, 4, 4);
insert into book2file (id, book_id, file_id)
values (5, 5, 5);
insert into book2file (id, book_id, file_id)
values (6, 6, 6);
insert into book2file (id, book_id, file_id)
values (7, 7, 7);
insert into book2file (id, book_id, file_id)
values (8, 8, 8);
insert into book2file (id, book_id, file_id)
values (9, 9, 9);
insert into book2file (id, book_id, file_id)
values (10, 10, 10);
insert into book2file (id, book_id, file_id)
values (11, 11, 11);
insert into book2file (id, book_id, file_id)
values (12, 12, 12);
insert into book2file (id, book_id, file_id)
values (13, 13, 13);
insert into book2file (id, book_id, file_id)
values (14, 14, 14);
insert into book2file (id, book_id, file_id)
values (15, 15, 15);
insert into book2file (id, book_id, file_id)
values (16, 16, 16);
insert into book2file (id, book_id, file_id)
values (17, 17, 17);
insert into book2file (id, book_id, file_id)
values (18, 18, 18);
insert into book2file (id, book_id, file_id)
values (19, 19, 19);
insert into book2file (id, book_id, file_id)
values (20, 20, 20);
insert into book2file (id, book_id, file_id)
values (21, 21, 21);
insert into book2file (id, book_id, file_id)
values (22, 22, 22);
insert into book2file (id, book_id, file_id)
values (23, 23, 23);
insert into book2file (id, book_id, file_id)
values (24, 24, 24);
insert into book2file (id, book_id, file_id)
values (25, 25, 25);
insert into book2file (id, book_id, file_id)
values (26, 26, 26);
insert into book2file (id, book_id, file_id)
values (27, 27, 27);
insert into book2file (id, book_id, file_id)
values (28, 28, 28);
insert into book2file (id, book_id, file_id)
values (29, 29, 29);
insert into book2file (id, book_id, file_id)
values (30, 30, 30);
insert into book2file (id, book_id, file_id)
values (31, 31, 31);
insert into book2file (id, book_id, file_id)
values (32, 32, 32);
insert into book2file (id, book_id, file_id)
values (33, 33, 33);
insert into book2file (id, book_id, file_id)
values (34, 34, 34);
insert into book2file (id, book_id, file_id)
values (35, 35, 35);
insert into book2file (id, book_id, file_id)
values (36, 36, 36);
insert into book2file (id, book_id, file_id)
values (37, 37, 37);
insert into book2file (id, book_id, file_id)
values (38, 38, 38);
insert into book2file (id, book_id, file_id)
values (39, 39, 39);
insert into book2file (id, book_id, file_id)
values (40, 40, 40);
insert into book2file (id, book_id, file_id)
values (41, 41, 41);
insert into book2file (id, book_id, file_id)
values (42, 42, 42);
insert into book2file (id, book_id, file_id)
values (43, 43, 43);
insert into book2file (id, book_id, file_id)
values (44, 44, 44);
insert into book2file (id, book_id, file_id)
values (45, 45, 45);
insert into book2file (id, book_id, file_id)
values (46, 46, 46);
insert into book2file (id, book_id, file_id)
values (47, 47, 47);
insert into book2file (id, book_id, file_id)
values (48, 48, 48);
insert into book2file (id, book_id, file_id)
values (49, 49, 49);
insert into book2file (id, book_id, file_id)
values (50, 50, 50);
insert into book2file (id, book_id, file_id)
values (51, 51, 51);
insert into book2file (id, book_id, file_id)
values (52, 52, 52);
insert into book2file (id, book_id, file_id)
values (53, 53, 53);
insert into book2file (id, book_id, file_id)
values (54, 54, 54);
insert into book2file (id, book_id, file_id)
values (55, 55, 55);
insert into book2file (id, book_id, file_id)
values (56, 56, 56);
insert into book2file (id, book_id, file_id)
values (57, 57, 57);
insert into book2file (id, book_id, file_id)
values (58, 58, 58);
insert into book2file (id, book_id, file_id)
values (59, 59, 59);
insert into book2file (id, book_id, file_id)
values (60, 60, 60);
insert into book2file (id, book_id, file_id)
values (61, 61, 61);
insert into book2file (id, book_id, file_id)
values (62, 62, 62);
insert into book2file (id, book_id, file_id)
values (63, 63, 63);
insert into book2file (id, book_id, file_id)
values (64, 64, 64);
insert into book2file (id, book_id, file_id)
values (65, 65, 65);
insert into book2file (id, book_id, file_id)
values (66, 66, 66);
insert into book2file (id, book_id, file_id)
values (67, 67, 67);
insert into book2file (id, book_id, file_id)
values (68, 68, 68);
insert into book2file (id, book_id, file_id)
values (69, 69, 69);
insert into book2file (id, book_id, file_id)
values (70, 70, 70);
insert into book2file (id, book_id, file_id)
values (71, 71, 71);
insert into book2file (id, book_id, file_id)
values (72, 72, 72);
insert into book2file (id, book_id, file_id)
values (73, 73, 73);
insert into book2file (id, book_id, file_id)
values (74, 74, 74);
insert into book2file (id, book_id, file_id)
values (75, 75, 75);
insert into book2file (id, book_id, file_id)
values (76, 76, 76);
insert into book2file (id, book_id, file_id)
values (77, 77, 77);
insert into book2file (id, book_id, file_id)
values (78, 78, 78);
insert into book2file (id, book_id, file_id)
values (79, 79, 79);
insert into book2file (id, book_id, file_id)
values (80, 80, 80);
insert into book2file (id, book_id, file_id)
values (81, 81, 81);
insert into book2file (id, book_id, file_id)
values (82, 82, 82);
insert into book2file (id, book_id, file_id)
values (83, 83, 83);
insert into book2file (id, book_id, file_id)
values (84, 84, 84);
insert into book2file (id, book_id, file_id)
values (85, 85, 85);
insert into book2file (id, book_id, file_id)
values (86, 86, 86);
insert into book2file (id, book_id, file_id)
values (87, 87, 87);
insert into book2file (id, book_id, file_id)
values (88, 88, 88);
insert into book2file (id, book_id, file_id)
values (89, 89, 89);
insert into book2file (id, book_id, file_id)
values (90, 90, 90);
insert into book2file (id, book_id, file_id)
values (91, 91, 91);
insert into book2file (id, book_id, file_id)
values (92, 92, 92);
insert into book2file (id, book_id, file_id)
values (93, 93, 93);
insert into book2file (id, book_id, file_id)
values (94, 94, 94);
insert into book2file (id, book_id, file_id)
values (95, 95, 95);
insert into book2file (id, book_id, file_id)
values (96, 96, 96);
insert into book2file (id, book_id, file_id)
values (97, 97, 97);
insert into book2file (id, book_id, file_id)
values (98, 98, 98);
insert into book2file (id, book_id, file_id)
values (99, 99, 99);
insert into book2file (id, book_id, file_id)
values (100, 100, 100);
insert into book2file (id, book_id, file_id)
values (101, 101, 101);
insert into book2file (id, book_id, file_id)
values (102, 102, 102);
insert into book2file (id, book_id, file_id)
values (103, 103, 103);
insert into book2file (id, book_id, file_id)
values (104, 104, 104);
insert into book2file (id, book_id, file_id)
values (105, 105, 105);
insert into book2file (id, book_id, file_id)
values (106, 106, 106);
insert into book2file (id, book_id, file_id)
values (107, 107, 107);
insert into book2file (id, book_id, file_id)
values (108, 108, 108);
insert into book2file (id, book_id, file_id)
values (109, 109, 109);
insert into book2file (id, book_id, file_id)
values (110, 110, 110);
insert into book2file (id, book_id, file_id)
values (111, 111, 111);
insert into book2file (id, book_id, file_id)
values (112, 112, 112);
insert into book2file (id, book_id, file_id)
values (113, 113, 113);
insert into book2file (id, book_id, file_id)
values (114, 114, 114);
insert into book2file (id, book_id, file_id)
values (115, 115, 115);
insert into book2file (id, book_id, file_id)
values (116, 116, 116);
insert into book2file (id, book_id, file_id)
values (117, 117, 117);
insert into book2file (id, book_id, file_id)
values (118, 118, 118);
insert into book2file (id, book_id, file_id)
values (119, 119, 119);
insert into book2file (id, book_id, file_id)
values (120, 120, 120);
insert into book2file (id, book_id, file_id)
values (121, 121, 121);
insert into book2file (id, book_id, file_id)
values (122, 122, 122);
insert into book2file (id, book_id, file_id)
values (123, 123, 123);
insert into book2file (id, book_id, file_id)
values (124, 124, 124);
insert into book2file (id, book_id, file_id)
values (125, 125, 125);
insert into book2file (id, book_id, file_id)
values (126, 126, 126);
insert into book2file (id, book_id, file_id)
values (127, 127, 127);
insert into book2file (id, book_id, file_id)
values (128, 128, 128);
insert into book2file (id, book_id, file_id)
values (129, 129, 129);
insert into book2file (id, book_id, file_id)
values (130, 130, 130);
insert into book2file (id, book_id, file_id)
values (131, 131, 131);
insert into book2file (id, book_id, file_id)
values (132, 132, 132);
insert into book2file (id, book_id, file_id)
values (133, 133, 133);
insert into book2file (id, book_id, file_id)
values (134, 134, 134);
insert into book2file (id, book_id, file_id)
values (135, 135, 135);
insert into book2file (id, book_id, file_id)
values (136, 136, 136);
insert into book2file (id, book_id, file_id)
values (137, 137, 137);
insert into book2file (id, book_id, file_id)
values (138, 138, 138);
insert into book2file (id, book_id, file_id)
values (139, 139, 139);
insert into book2file (id, book_id, file_id)
values (140, 140, 140);
insert into book2file (id, book_id, file_id)
values (141, 141, 141);
insert into book2file (id, book_id, file_id)
values (142, 142, 142);
insert into book2file (id, book_id, file_id)
values (143, 143, 143);
insert into book2file (id, book_id, file_id)
values (144, 144, 144);
insert into book2file (id, book_id, file_id)
values (145, 145, 145);
insert into book2file (id, book_id, file_id)
values (146, 146, 146);
insert into book2file (id, book_id, file_id)
values (147, 147, 147);
insert into book2file (id, book_id, file_id)
values (148, 148, 148);
insert into book2file (id, book_id, file_id)
values (149, 149, 149);
insert into book2file (id, book_id, file_id)
values (150, 150, 150);
insert into book2file (id, book_id, file_id)
values (151, 151, 151);
insert into book2file (id, book_id, file_id)
values (152, 152, 152);
insert into book2file (id, book_id, file_id)
values (153, 153, 153);
insert into book2file (id, book_id, file_id)
values (154, 154, 154);
insert into book2file (id, book_id, file_id)
values (155, 155, 155);
insert into book2file (id, book_id, file_id)
values (156, 156, 156);
insert into book2file (id, book_id, file_id)
values (157, 157, 157);
insert into book2file (id, book_id, file_id)
values (158, 158, 158);
insert into book2file (id, book_id, file_id)
values (159, 159, 159);
insert into book2file (id, book_id, file_id)
values (160, 160, 160);
insert into book2file (id, book_id, file_id)
values (161, 161, 161);
insert into book2file (id, book_id, file_id)
values (162, 162, 162);
insert into book2file (id, book_id, file_id)
values (163, 163, 163);
insert into book2file (id, book_id, file_id)
values (164, 164, 164);
insert into book2file (id, book_id, file_id)
values (165, 165, 165);
insert into book2file (id, book_id, file_id)
values (166, 166, 166);
insert into book2file (id, book_id, file_id)
values (167, 167, 167);
insert into book2file (id, book_id, file_id)
values (168, 168, 168);
insert into book2file (id, book_id, file_id)
values (169, 169, 169);
insert into book2file (id, book_id, file_id)
values (170, 170, 170);
insert into book2file (id, book_id, file_id)
values (171, 171, 171);
insert into book2file (id, book_id, file_id)
values (172, 172, 172);
insert into book2file (id, book_id, file_id)
values (173, 173, 173);
insert into book2file (id, book_id, file_id)
values (174, 174, 174);
insert into book2file (id, book_id, file_id)
values (175, 175, 175);
insert into book2file (id, book_id, file_id)
values (176, 176, 176);
insert into book2file (id, book_id, file_id)
values (177, 177, 177);
insert into book2file (id, book_id, file_id)
values (178, 178, 178);
insert into book2file (id, book_id, file_id)
values (179, 179, 179);
insert into book2file (id, book_id, file_id)
values (180, 180, 180);
insert into book2file (id, book_id, file_id)
values (181, 181, 181);
insert into book2file (id, book_id, file_id)
values (182, 182, 182);
insert into book2file (id, book_id, file_id)
values (183, 183, 183);
insert into book2file (id, book_id, file_id)
values (184, 184, 184);
insert into book2file (id, book_id, file_id)
values (185, 185, 185);
insert into book2file (id, book_id, file_id)
values (186, 186, 186);
insert into book2file (id, book_id, file_id)
values (187, 187, 187);
insert into book2file (id, book_id, file_id)
values (188, 188, 188);
insert into book2file (id, book_id, file_id)
values (189, 189, 189);
insert into book2file (id, book_id, file_id)
values (190, 190, 190);
insert into book2file (id, book_id, file_id)
values (191, 191, 191);
insert into book2file (id, book_id, file_id)
values (192, 192, 192);
insert into book2file (id, book_id, file_id)
values (193, 193, 193);
insert into book2file (id, book_id, file_id)
values (194, 194, 194);
insert into book2file (id, book_id, file_id)
values (195, 195, 195);
insert into book2file (id, book_id, file_id)
values (196, 196, 196);
insert into book2file (id, book_id, file_id)
values (197, 197, 197);
insert into book2file (id, book_id, file_id)
values (198, 198, 198);
insert into book2file (id, book_id, file_id)
values (199, 199, 199);
insert into book2file (id, book_id, file_id)
values (200, 200, 200);
insert into book2file (id, book_id, file_id)
values (201, 201, 201);
insert into book2file (id, book_id, file_id)
values (202, 202, 202);
insert into book2file (id, book_id, file_id)
values (203, 203, 203);
insert into book2file (id, book_id, file_id)
values (204, 204, 204);
insert into book2file (id, book_id, file_id)
values (205, 205, 205);
insert into book2file (id, book_id, file_id)
values (206, 206, 206);
insert into book2file (id, book_id, file_id)
values (207, 207, 207);
insert into book2file (id, book_id, file_id)
values (208, 208, 208);
insert into book2file (id, book_id, file_id)
values (209, 209, 209);
insert into book2file (id, book_id, file_id)
values (210, 210, 210);
insert into book2file (id, book_id, file_id)
values (211, 211, 211);
insert into book2file (id, book_id, file_id)
values (212, 212, 212);
insert into book2file (id, book_id, file_id)
values (213, 213, 213);
insert into book2file (id, book_id, file_id)
values (214, 214, 214);
insert into book2file (id, book_id, file_id)
values (215, 215, 215);
insert into book2file (id, book_id, file_id)
values (216, 216, 216);
insert into book2file (id, book_id, file_id)
values (217, 217, 217);
insert into book2file (id, book_id, file_id)
values (218, 218, 218);
insert into book2file (id, book_id, file_id)
values (219, 219, 219);
insert into book2file (id, book_id, file_id)
values (220, 220, 220);
insert into book2file (id, book_id, file_id)
values (221, 221, 221);
insert into book2file (id, book_id, file_id)
values (222, 222, 222);
insert into book2file (id, book_id, file_id)
values (223, 223, 223);
insert into book2file (id, book_id, file_id)
values (224, 224, 224);
insert into book2file (id, book_id, file_id)
values (225, 225, 225);
insert into book2file (id, book_id, file_id)
values (226, 226, 226);
insert into book2file (id, book_id, file_id)
values (227, 227, 227);
insert into book2file (id, book_id, file_id)
values (228, 228, 228);
insert into book2file (id, book_id, file_id)
values (229, 229, 229);
insert into book2file (id, book_id, file_id)
values (230, 230, 230);
insert into book2file (id, book_id, file_id)
values (231, 231, 231);
insert into book2file (id, book_id, file_id)
values (232, 232, 232);
insert into book2file (id, book_id, file_id)
values (233, 233, 233);
insert into book2file (id, book_id, file_id)
values (234, 234, 234);
insert into book2file (id, book_id, file_id)
values (235, 235, 235);
insert into book2file (id, book_id, file_id)
values (236, 236, 236);
insert into book2file (id, book_id, file_id)
values (237, 237, 237);
insert into book2file (id, book_id, file_id)
values (238, 238, 238);
insert into book2file (id, book_id, file_id)
values (239, 239, 239);
insert into book2file (id, book_id, file_id)
values (240, 240, 240);
insert into book2file (id, book_id, file_id)
values (241, 241, 241);
insert into book2file (id, book_id, file_id)
values (242, 242, 242);
insert into book2file (id, book_id, file_id)
values (243, 243, 243);
insert into book2file (id, book_id, file_id)
values (244, 244, 244);
insert into book2file (id, book_id, file_id)
values (245, 245, 245);
insert into book2file (id, book_id, file_id)
values (246, 246, 246);
insert into book2file (id, book_id, file_id)
values (247, 247, 247);
insert into book2file (id, book_id, file_id)
values (248, 248, 248);
insert into book2file (id, book_id, file_id)
values (249, 249, 249);
insert into book2file (id, book_id, file_id)
values (250, 250, 250);
insert into book2file (id, book_id, file_id)
values (251, 251, 251);
insert into book2file (id, book_id, file_id)
values (252, 252, 252);
insert into book2file (id, book_id, file_id)
values (253, 253, 253);
insert into book2file (id, book_id, file_id)
values (254, 254, 254);
insert into book2file (id, book_id, file_id)
values (255, 255, 255);
insert into book2file (id, book_id, file_id)
values (256, 256, 256);
insert into book2file (id, book_id, file_id)
values (257, 257, 257);
insert into book2file (id, book_id, file_id)
values (258, 258, 258);
insert into book2file (id, book_id, file_id)
values (259, 259, 259);
insert into book2file (id, book_id, file_id)
values (260, 260, 260);
insert into book2file (id, book_id, file_id)
values (261, 261, 261);
insert into book2file (id, book_id, file_id)
values (262, 262, 262);
insert into book2file (id, book_id, file_id)
values (263, 263, 263);
insert into book2file (id, book_id, file_id)
values (264, 264, 264);
insert into book2file (id, book_id, file_id)
values (265, 265, 265);
insert into book2file (id, book_id, file_id)
values (266, 266, 266);
insert into book2file (id, book_id, file_id)
values (267, 267, 267);
insert into book2file (id, book_id, file_id)
values (268, 268, 268);
insert into book2file (id, book_id, file_id)
values (269, 269, 269);
insert into book2file (id, book_id, file_id)
values (270, 270, 270);
insert into book2file (id, book_id, file_id)
values (271, 271, 271);
insert into book2file (id, book_id, file_id)
values (272, 272, 272);
insert into book2file (id, book_id, file_id)
values (273, 273, 273);
insert into book2file (id, book_id, file_id)
values (274, 274, 274);
insert into book2file (id, book_id, file_id)
values (275, 275, 275);
insert into book2file (id, book_id, file_id)
values (276, 276, 276);
insert into book2file (id, book_id, file_id)
values (277, 277, 277);
insert into book2file (id, book_id, file_id)
values (278, 278, 278);
insert into book2file (id, book_id, file_id)
values (279, 279, 279);
insert into book2file (id, book_id, file_id)
values (280, 280, 280);
insert into book2file (id, book_id, file_id)
values (281, 281, 281);
insert into book2file (id, book_id, file_id)
values (282, 282, 282);
insert into book2file (id, book_id, file_id)
values (283, 283, 283);
insert into book2file (id, book_id, file_id)
values (284, 284, 284);
insert into book2file (id, book_id, file_id)
values (285, 285, 285);
insert into book2file (id, book_id, file_id)
values (286, 286, 286);
insert into book2file (id, book_id, file_id)
values (287, 287, 287);
insert into book2file (id, book_id, file_id)
values (288, 288, 288);
insert into book2file (id, book_id, file_id)
values (289, 289, 289);
insert into book2file (id, book_id, file_id)
values (290, 290, 290);
insert into book2file (id, book_id, file_id)
values (291, 291, 291);
insert into book2file (id, book_id, file_id)
values (292, 292, 292);
insert into book2file (id, book_id, file_id)
values (293, 293, 293);
insert into book2file (id, book_id, file_id)
values (294, 294, 294);
insert into book2file (id, book_id, file_id)
values (295, 295, 295);
insert into book2file (id, book_id, file_id)
values (296, 296, 296);
insert into book2file (id, book_id, file_id)
values (297, 297, 297);
insert into book2file (id, book_id, file_id)
values (298, 298, 298);
insert into book2file (id, book_id, file_id)
values (299, 299, 299);
insert into book2file (id, book_id, file_id)
values (300, 300, 300);
insert into book2file (id, book_id, file_id)
values (301, 301, 301);
insert into book2file (id, book_id, file_id)
values (302, 302, 302);
insert into book2file (id, book_id, file_id)
values (303, 303, 303);
insert into book2file (id, book_id, file_id)
values (304, 304, 304);
insert into book2file (id, book_id, file_id)
values (305, 305, 305);
insert into book2file (id, book_id, file_id)
values (306, 306, 306);
insert into book2file (id, book_id, file_id)
values (307, 307, 307);
insert into book2file (id, book_id, file_id)
values (308, 308, 308);
insert into book2file (id, book_id, file_id)
values (309, 309, 309);
insert into book2file (id, book_id, file_id)
values (310, 310, 310);
insert into book2file (id, book_id, file_id)
values (311, 311, 311);
insert into book2file (id, book_id, file_id)
values (312, 312, 312);
insert into book2file (id, book_id, file_id)
values (313, 313, 313);
insert into book2file (id, book_id, file_id)
values (314, 314, 314);
insert into book2file (id, book_id, file_id)
values (315, 315, 315);
insert into book2file (id, book_id, file_id)
values (316, 316, 316);
insert into book2file (id, book_id, file_id)
values (317, 317, 317);
insert into book2file (id, book_id, file_id)
values (318, 318, 318);
insert into book2file (id, book_id, file_id)
values (319, 319, 319);
insert into book2file (id, book_id, file_id)
values (320, 320, 320);
insert into book2file (id, book_id, file_id)
values (321, 321, 321);
insert into book2file (id, book_id, file_id)
values (322, 322, 322);
insert into book2file (id, book_id, file_id)
values (323, 323, 323);
insert into book2file (id, book_id, file_id)
values (324, 324, 324);
insert into book2file (id, book_id, file_id)
values (325, 325, 325);
insert into book2file (id, book_id, file_id)
values (326, 326, 326);
insert into book2file (id, book_id, file_id)
values (327, 327, 327);
insert into book2file (id, book_id, file_id)
values (328, 328, 328);
insert into book2file (id, book_id, file_id)
values (329, 329, 329);
insert into book2file (id, book_id, file_id)
values (330, 330, 330);
insert into book2file (id, book_id, file_id)
values (331, 331, 331);
insert into book2file (id, book_id, file_id)
values (332, 332, 332);
insert into book2file (id, book_id, file_id)
values (333, 333, 333);
insert into book2file (id, book_id, file_id)
values (334, 334, 334);
insert into book2file (id, book_id, file_id)
values (335, 335, 335);
insert into book2file (id, book_id, file_id)
values (336, 336, 336);
insert into book2file (id, book_id, file_id)
values (337, 337, 337);
insert into book2file (id, book_id, file_id)
values (338, 338, 338);
insert into book2file (id, book_id, file_id)
values (339, 339, 339);
insert into book2file (id, book_id, file_id)
values (340, 340, 340);
insert into book2file (id, book_id, file_id)
values (341, 341, 341);
insert into book2file (id, book_id, file_id)
values (342, 342, 342);
insert into book2file (id, book_id, file_id)
values (343, 343, 343);
insert into book2file (id, book_id, file_id)
values (344, 344, 344);
insert into book2file (id, book_id, file_id)
values (345, 345, 345);
insert into book2file (id, book_id, file_id)
values (346, 346, 346);
insert into book2file (id, book_id, file_id)
values (347, 347, 347);
insert into book2file (id, book_id, file_id)
values (348, 348, 348);
insert into book2file (id, book_id, file_id)
values (349, 349, 349);
insert into book2file (id, book_id, file_id)
values (350, 350, 350);
insert into book2file (id, book_id, file_id)
values (351, 351, 351);
insert into book2file (id, book_id, file_id)
values (352, 352, 352);
insert into book2file (id, book_id, file_id)
values (353, 353, 353);
insert into book2file (id, book_id, file_id)
values (354, 354, 354);
insert into book2file (id, book_id, file_id)
values (355, 355, 355);
insert into book2file (id, book_id, file_id)
values (356, 356, 356);
insert into book2file (id, book_id, file_id)
values (357, 357, 357);
insert into book2file (id, book_id, file_id)
values (358, 358, 358);
insert into book2file (id, book_id, file_id)
values (359, 359, 359);
insert into book2file (id, book_id, file_id)
values (360, 360, 360);
insert into book2file (id, book_id, file_id)
values (361, 361, 361);
insert into book2file (id, book_id, file_id)
values (362, 362, 362);
insert into book2file (id, book_id, file_id)
values (363, 363, 363);
insert into book2file (id, book_id, file_id)
values (364, 364, 364);
insert into book2file (id, book_id, file_id)
values (365, 365, 365);
insert into book2file (id, book_id, file_id)
values (366, 366, 366);
insert into book2file (id, book_id, file_id)
values (367, 367, 367);
insert into book2file (id, book_id, file_id)
values (368, 368, 368);
insert into book2file (id, book_id, file_id)
values (369, 369, 369);
insert into book2file (id, book_id, file_id)
values (370, 370, 370);
insert into book2file (id, book_id, file_id)
values (371, 371, 371);
insert into book2file (id, book_id, file_id)
values (372, 372, 372);
insert into book2file (id, book_id, file_id)
values (373, 373, 373);
insert into book2file (id, book_id, file_id)
values (374, 374, 374);
insert into book2file (id, book_id, file_id)
values (375, 375, 375);
insert into book2file (id, book_id, file_id)
values (376, 376, 376);
insert into book2file (id, book_id, file_id)
values (377, 377, 377);
insert into book2file (id, book_id, file_id)
values (378, 378, 378);
insert into book2file (id, book_id, file_id)
values (379, 379, 379);
insert into book2file (id, book_id, file_id)
values (380, 380, 380);
insert into book2file (id, book_id, file_id)
values (381, 381, 381);
insert into book2file (id, book_id, file_id)
values (382, 382, 382);
insert into book2file (id, book_id, file_id)
values (383, 383, 383);
insert into book2file (id, book_id, file_id)
values (384, 384, 384);
insert into book2file (id, book_id, file_id)
values (385, 385, 385);
insert into book2file (id, book_id, file_id)
values (386, 386, 386);
insert into book2file (id, book_id, file_id)
values (387, 387, 387);
insert into book2file (id, book_id, file_id)
values (388, 388, 388);
insert into book2file (id, book_id, file_id)
values (389, 389, 389);
insert into book2file (id, book_id, file_id)
values (390, 390, 390);
insert into book2file (id, book_id, file_id)
values (391, 391, 391);
insert into book2file (id, book_id, file_id)
values (392, 392, 392);
insert into book2file (id, book_id, file_id)
values (393, 393, 393);
insert into book2file (id, book_id, file_id)
values (394, 394, 394);
insert into book2file (id, book_id, file_id)
values (395, 395, 395);
insert into book2file (id, book_id, file_id)
values (396, 396, 396);
insert into book2file (id, book_id, file_id)
values (397, 397, 397);
insert into book2file (id, book_id, file_id)
values (398, 398, 398);
insert into book2file (id, book_id, file_id)
values (399, 399, 399);
insert into book2file (id, book_id, file_id)
values (400, 400, 400);
insert into book2file (id, book_id, file_id)
values (401, 401, 401);
insert into book2file (id, book_id, file_id)
values (402, 402, 402);
insert into book2file (id, book_id, file_id)
values (403, 403, 403);
insert into book2file (id, book_id, file_id)
values (404, 404, 404);
insert into book2file (id, book_id, file_id)
values (405, 405, 405);
insert into book2file (id, book_id, file_id)
values (406, 406, 406);
insert into book2file (id, book_id, file_id)
values (407, 407, 407);
insert into book2file (id, book_id, file_id)
values (408, 408, 408);
insert into book2file (id, book_id, file_id)
values (409, 409, 409);
insert into book2file (id, book_id, file_id)
values (410, 410, 410);
insert into book2file (id, book_id, file_id)
values (411, 411, 411);
insert into book2file (id, book_id, file_id)
values (412, 412, 412);
insert into book2file (id, book_id, file_id)
values (413, 413, 413);
insert into book2file (id, book_id, file_id)
values (414, 414, 414);
insert into book2file (id, book_id, file_id)
values (415, 415, 415);
insert into book2file (id, book_id, file_id)
values (416, 416, 416);
insert into book2file (id, book_id, file_id)
values (417, 417, 417);
insert into book2file (id, book_id, file_id)
values (418, 418, 418);
insert into book2file (id, book_id, file_id)
values (419, 419, 419);
insert into book2file (id, book_id, file_id)
values (420, 420, 420);
insert into book2file (id, book_id, file_id)
values (421, 421, 421);
insert into book2file (id, book_id, file_id)
values (422, 422, 422);
insert into book2file (id, book_id, file_id)
values (423, 423, 423);
insert into book2file (id, book_id, file_id)
values (424, 424, 424);
insert into book2file (id, book_id, file_id)
values (425, 425, 425);
insert into book2file (id, book_id, file_id)
values (426, 426, 426);
insert into book2file (id, book_id, file_id)
values (427, 427, 427);
insert into book2file (id, book_id, file_id)
values (428, 428, 428);
insert into book2file (id, book_id, file_id)
values (429, 429, 429);
insert into book2file (id, book_id, file_id)
values (430, 430, 430);
insert into book2file (id, book_id, file_id)
values (431, 431, 431);
insert into book2file (id, book_id, file_id)
values (432, 432, 432);
insert into book2file (id, book_id, file_id)
values (433, 433, 433);
insert into book2file (id, book_id, file_id)
values (434, 434, 434);
insert into book2file (id, book_id, file_id)
values (435, 435, 435);
insert into book2file (id, book_id, file_id)
values (436, 436, 436);
insert into book2file (id, book_id, file_id)
values (437, 437, 437);
insert into book2file (id, book_id, file_id)
values (438, 438, 438);
insert into book2file (id, book_id, file_id)
values (439, 439, 439);
insert into book2file (id, book_id, file_id)
values (440, 440, 440);
insert into book2file (id, book_id, file_id)
values (441, 441, 441);
insert into book2file (id, book_id, file_id)
values (442, 442, 442);
insert into book2file (id, book_id, file_id)
values (443, 443, 443);
insert into book2file (id, book_id, file_id)
values (444, 444, 444);
insert into book2file (id, book_id, file_id)
values (445, 445, 445);
insert into book2file (id, book_id, file_id)
values (446, 446, 446);
insert into book2file (id, book_id, file_id)
values (447, 447, 447);
insert into book2file (id, book_id, file_id)
values (448, 448, 448);
insert into book2file (id, book_id, file_id)
values (449, 449, 449);
insert into book2file (id, book_id, file_id)
values (450, 450, 450);
insert into book2file (id, book_id, file_id)
values (451, 451, 451);
insert into book2file (id, book_id, file_id)
values (452, 452, 452);
insert into book2file (id, book_id, file_id)
values (453, 453, 453);
insert into book2file (id, book_id, file_id)
values (454, 454, 454);
insert into book2file (id, book_id, file_id)
values (455, 455, 455);
insert into book2file (id, book_id, file_id)
values (456, 456, 456);
insert into book2file (id, book_id, file_id)
values (457, 457, 457);
insert into book2file (id, book_id, file_id)
values (458, 458, 458);
insert into book2file (id, book_id, file_id)
values (459, 459, 459);
insert into book2file (id, book_id, file_id)
values (460, 460, 460);
insert into book2file (id, book_id, file_id)
values (461, 461, 461);
insert into book2file (id, book_id, file_id)
values (462, 462, 462);
insert into book2file (id, book_id, file_id)
values (463, 463, 463);
insert into book2file (id, book_id, file_id)
values (464, 464, 464);
insert into book2file (id, book_id, file_id)
values (465, 465, 465);
insert into book2file (id, book_id, file_id)
values (466, 466, 466);
insert into book2file (id, book_id, file_id)
values (467, 467, 467);
insert into book2file (id, book_id, file_id)
values (468, 468, 468);
insert into book2file (id, book_id, file_id)
values (469, 469, 469);
insert into book2file (id, book_id, file_id)
values (470, 470, 470);
insert into book2file (id, book_id, file_id)
values (471, 471, 471);
insert into book2file (id, book_id, file_id)
values (472, 472, 472);
insert into book2file (id, book_id, file_id)
values (473, 473, 473);
insert into book2file (id, book_id, file_id)
values (474, 474, 474);
insert into book2file (id, book_id, file_id)
values (475, 475, 475);
insert into book2file (id, book_id, file_id)
values (476, 476, 476);
insert into book2file (id, book_id, file_id)
values (477, 477, 477);
insert into book2file (id, book_id, file_id)
values (478, 478, 478);
insert into book2file (id, book_id, file_id)
values (479, 479, 479);
insert into book2file (id, book_id, file_id)
values (480, 480, 480);
insert into book2file (id, book_id, file_id)
values (481, 481, 481);
insert into book2file (id, book_id, file_id)
values (482, 482, 482);
insert into book2file (id, book_id, file_id)
values (483, 483, 483);
insert into book2file (id, book_id, file_id)
values (484, 484, 484);
insert into book2file (id, book_id, file_id)
values (485, 485, 485);
insert into book2file (id, book_id, file_id)
values (486, 486, 486);
insert into book2file (id, book_id, file_id)
values (487, 487, 487);
insert into book2file (id, book_id, file_id)
values (488, 488, 488);
insert into book2file (id, book_id, file_id)
values (489, 489, 489);
insert into book2file (id, book_id, file_id)
values (490, 490, 490);
insert into book2file (id, book_id, file_id)
values (491, 491, 491);
insert into book2file (id, book_id, file_id)
values (492, 492, 492);
insert into book2file (id, book_id, file_id)
values (493, 493, 493);
insert into book2file (id, book_id, file_id)
values (494, 494, 494);
insert into book2file (id, book_id, file_id)
values (495, 495, 495);
insert into book2file (id, book_id, file_id)
values (496, 496, 496);
insert into book2file (id, book_id, file_id)
values (497, 497, 497);
insert into book2file (id, book_id, file_id)
values (498, 498, 498);
insert into book2file (id, book_id, file_id)
values (499, 499, 499);
insert into book2file (id, book_id, file_id)
values (500, 500, 500);
insert into book2file (id, book_id, file_id)
values (501, 501, 501);
insert into book2file (id, book_id, file_id)
values (502, 502, 502);
insert into book2file (id, book_id, file_id)
values (503, 503, 503);
insert into book2file (id, book_id, file_id)
values (504, 504, 504);
insert into book2file (id, book_id, file_id)
values (505, 505, 505);
insert into book2file (id, book_id, file_id)
values (506, 506, 506);
insert into book2file (id, book_id, file_id)
values (507, 507, 507);
insert into book2file (id, book_id, file_id)
values (508, 508, 508);
insert into book2file (id, book_id, file_id)
values (509, 509, 509);
insert into book2file (id, book_id, file_id)
values (510, 510, 510);
insert into book2file (id, book_id, file_id)
values (511, 511, 511);
insert into book2file (id, book_id, file_id)
values (512, 512, 512);
insert into book2file (id, book_id, file_id)
values (513, 513, 513);
insert into book2file (id, book_id, file_id)
values (514, 514, 514);
insert into book2file (id, book_id, file_id)
values (515, 515, 515);
insert into book2file (id, book_id, file_id)
values (516, 516, 516);
insert into book2file (id, book_id, file_id)
values (517, 517, 517);
insert into book2file (id, book_id, file_id)
values (518, 518, 518);
insert into book2file (id, book_id, file_id)
values (519, 519, 519);
insert into book2file (id, book_id, file_id)
values (520, 520, 520);
insert into book2file (id, book_id, file_id)
values (521, 521, 521);
insert into book2file (id, book_id, file_id)
values (522, 522, 522);
insert into book2file (id, book_id, file_id)
values (523, 523, 523);
insert into book2file (id, book_id, file_id)
values (524, 524, 524);
insert into book2file (id, book_id, file_id)
values (525, 525, 525);
insert into book2file (id, book_id, file_id)
values (526, 526, 526);
insert into book2file (id, book_id, file_id)
values (527, 527, 527);
insert into book2file (id, book_id, file_id)
values (528, 528, 528);
insert into book2file (id, book_id, file_id)
values (529, 529, 529);
insert into book2file (id, book_id, file_id)
values (530, 530, 530);
insert into book2file (id, book_id, file_id)
values (531, 531, 531);
insert into book2file (id, book_id, file_id)
values (532, 532, 532);
insert into book2file (id, book_id, file_id)
values (533, 533, 533);
insert into book2file (id, book_id, file_id)
values (534, 534, 534);
insert into book2file (id, book_id, file_id)
values (535, 535, 535);
insert into book2file (id, book_id, file_id)
values (536, 536, 536);
insert into book2file (id, book_id, file_id)
values (537, 537, 537);
insert into book2file (id, book_id, file_id)
values (538, 538, 538);
insert into book2file (id, book_id, file_id)
values (539, 539, 539);
insert into book2file (id, book_id, file_id)
values (540, 540, 540);
insert into book2file (id, book_id, file_id)
values (541, 541, 541);
insert into book2file (id, book_id, file_id)
values (542, 542, 542);
insert into book2file (id, book_id, file_id)
values (543, 543, 543);
insert into book2file (id, book_id, file_id)
values (544, 544, 544);
insert into book2file (id, book_id, file_id)
values (545, 545, 545);
insert into book2file (id, book_id, file_id)
values (546, 546, 546);
insert into book2file (id, book_id, file_id)
values (547, 547, 547);
insert into book2file (id, book_id, file_id)
values (548, 548, 548);
insert into book2file (id, book_id, file_id)
values (549, 549, 549);
insert into book2file (id, book_id, file_id)
values (550, 550, 550);
insert into book2file (id, book_id, file_id)
values (551, 551, 551);
insert into book2file (id, book_id, file_id)
values (552, 552, 552);
insert into book2file (id, book_id, file_id)
values (553, 553, 553);
insert into book2file (id, book_id, file_id)
values (554, 554, 554);
insert into book2file (id, book_id, file_id)
values (555, 555, 555);
insert into book2file (id, book_id, file_id)
values (556, 556, 556);
insert into book2file (id, book_id, file_id)
values (557, 557, 557);
insert into book2file (id, book_id, file_id)
values (558, 558, 558);
insert into book2file (id, book_id, file_id)
values (559, 559, 559);
insert into book2file (id, book_id, file_id)
values (560, 560, 560);
insert into book2file (id, book_id, file_id)
values (561, 561, 561);
insert into book2file (id, book_id, file_id)
values (562, 562, 562);
insert into book2file (id, book_id, file_id)
values (563, 563, 563);
insert into book2file (id, book_id, file_id)
values (564, 564, 564);
insert into book2file (id, book_id, file_id)
values (565, 565, 565);
insert into book2file (id, book_id, file_id)
values (566, 566, 566);
insert into book2file (id, book_id, file_id)
values (567, 567, 567);
insert into book2file (id, book_id, file_id)
values (568, 568, 568);
insert into book2file (id, book_id, file_id)
values (569, 569, 569);
insert into book2file (id, book_id, file_id)
values (570, 570, 570);
insert into book2file (id, book_id, file_id)
values (571, 571, 571);
insert into book2file (id, book_id, file_id)
values (572, 572, 572);
insert into book2file (id, book_id, file_id)
values (573, 573, 573);
insert into book2file (id, book_id, file_id)
values (574, 574, 574);
insert into book2file (id, book_id, file_id)
values (575, 575, 575);
insert into book2file (id, book_id, file_id)
values (576, 576, 576);
insert into book2file (id, book_id, file_id)
values (577, 577, 577);
insert into book2file (id, book_id, file_id)
values (578, 578, 578);
insert into book2file (id, book_id, file_id)
values (579, 579, 579);
insert into book2file (id, book_id, file_id)
values (580, 580, 580);
insert into book2file (id, book_id, file_id)
values (581, 581, 581);
insert into book2file (id, book_id, file_id)
values (582, 582, 582);
insert into book2file (id, book_id, file_id)
values (583, 583, 583);
insert into book2file (id, book_id, file_id)
values (584, 584, 584);
insert into book2file (id, book_id, file_id)
values (585, 585, 585);
insert into book2file (id, book_id, file_id)
values (586, 586, 586);
insert into book2file (id, book_id, file_id)
values (587, 587, 587);
insert into book2file (id, book_id, file_id)
values (588, 588, 588);
insert into book2file (id, book_id, file_id)
values (589, 589, 589);
insert into book2file (id, book_id, file_id)
values (590, 590, 590);
insert into book2file (id, book_id, file_id)
values (591, 591, 591);
insert into book2file (id, book_id, file_id)
values (592, 592, 592);
insert into book2file (id, book_id, file_id)
values (593, 593, 593);
insert into book2file (id, book_id, file_id)
values (594, 594, 594);
insert into book2file (id, book_id, file_id)
values (595, 595, 595);
insert into book2file (id, book_id, file_id)
values (596, 596, 596);
insert into book2file (id, book_id, file_id)
values (597, 597, 597);
insert into book2file (id, book_id, file_id)
values (598, 598, 598);
insert into book2file (id, book_id, file_id)
values (599, 599, 599);
insert into book2file (id, book_id, file_id)
values (600, 600, 600);
insert into book2file (id, book_id, file_id)
values (601, 601, 601);
insert into book2file (id, book_id, file_id)
values (602, 602, 602);
insert into book2file (id, book_id, file_id)
values (603, 603, 603);
insert into book2file (id, book_id, file_id)
values (604, 604, 604);
insert into book2file (id, book_id, file_id)
values (605, 605, 605);
insert into book2file (id, book_id, file_id)
values (606, 606, 606);
insert into book2file (id, book_id, file_id)
values (607, 607, 607);
insert into book2file (id, book_id, file_id)
values (608, 608, 608);
insert into book2file (id, book_id, file_id)
values (609, 609, 609);
insert into book2file (id, book_id, file_id)
values (610, 610, 610);
insert into book2file (id, book_id, file_id)
values (611, 611, 611);
insert into book2file (id, book_id, file_id)
values (612, 612, 612);
insert into book2file (id, book_id, file_id)
values (613, 613, 613);
insert into book2file (id, book_id, file_id)
values (614, 614, 614);
insert into book2file (id, book_id, file_id)
values (615, 615, 615);
insert into book2file (id, book_id, file_id)
values (616, 616, 616);
insert into book2file (id, book_id, file_id)
values (617, 617, 617);
insert into book2file (id, book_id, file_id)
values (618, 618, 618);
insert into book2file (id, book_id, file_id)
values (619, 619, 619);
insert into book2file (id, book_id, file_id)
values (620, 620, 620);
insert into book2file (id, book_id, file_id)
values (621, 621, 621);
insert into book2file (id, book_id, file_id)
values (622, 622, 622);
insert into book2file (id, book_id, file_id)
values (623, 623, 623);
insert into book2file (id, book_id, file_id)
values (624, 624, 624);
insert into book2file (id, book_id, file_id)
values (625, 625, 625);
insert into book2file (id, book_id, file_id)
values (626, 626, 626);
insert into book2file (id, book_id, file_id)
values (627, 627, 627);
insert into book2file (id, book_id, file_id)
values (628, 628, 628);
insert into book2file (id, book_id, file_id)
values (629, 629, 629);
insert into book2file (id, book_id, file_id)
values (630, 630, 630);
insert into book2file (id, book_id, file_id)
values (631, 631, 631);
insert into book2file (id, book_id, file_id)
values (632, 632, 632);
insert into book2file (id, book_id, file_id)
values (633, 633, 633);
insert into book2file (id, book_id, file_id)
values (634, 634, 634);
insert into book2file (id, book_id, file_id)
values (635, 635, 635);
insert into book2file (id, book_id, file_id)
values (636, 636, 636);
insert into book2file (id, book_id, file_id)
values (637, 637, 637);
insert into book2file (id, book_id, file_id)
values (638, 638, 638);
insert into book2file (id, book_id, file_id)
values (639, 639, 639);
insert into book2file (id, book_id, file_id)
values (640, 640, 640);
insert into book2file (id, book_id, file_id)
values (641, 641, 641);
insert into book2file (id, book_id, file_id)
values (642, 642, 642);
insert into book2file (id, book_id, file_id)
values (643, 643, 643);
insert into book2file (id, book_id, file_id)
values (644, 644, 644);
insert into book2file (id, book_id, file_id)
values (645, 645, 645);
insert into book2file (id, book_id, file_id)
values (646, 646, 646);
insert into book2file (id, book_id, file_id)
values (647, 647, 647);
insert into book2file (id, book_id, file_id)
values (648, 648, 648);
insert into book2file (id, book_id, file_id)
values (649, 649, 649);
insert into book2file (id, book_id, file_id)
values (650, 650, 650);
insert into book2file (id, book_id, file_id)
values (651, 651, 651);
insert into book2file (id, book_id, file_id)
values (652, 652, 652);
insert into book2file (id, book_id, file_id)
values (653, 653, 653);
insert into book2file (id, book_id, file_id)
values (654, 654, 654);
insert into book2file (id, book_id, file_id)
values (655, 655, 655);
insert into book2file (id, book_id, file_id)
values (656, 656, 656);
insert into book2file (id, book_id, file_id)
values (657, 657, 657);
insert into book2file (id, book_id, file_id)
values (658, 658, 658);
insert into book2file (id, book_id, file_id)
values (659, 659, 659);
insert into book2file (id, book_id, file_id)
values (660, 660, 660);
insert into book2file (id, book_id, file_id)
values (661, 661, 661);
insert into book2file (id, book_id, file_id)
values (662, 662, 662);
insert into book2file (id, book_id, file_id)
values (663, 663, 663);
insert into book2file (id, book_id, file_id)
values (664, 664, 664);
insert into book2file (id, book_id, file_id)
values (665, 665, 665);
insert into book2file (id, book_id, file_id)
values (666, 666, 666);
insert into book2file (id, book_id, file_id)
values (667, 667, 667);
insert into book2file (id, book_id, file_id)
values (668, 668, 668);
insert into book2file (id, book_id, file_id)
values (669, 669, 669);
insert into book2file (id, book_id, file_id)
values (670, 670, 670);
insert into book2file (id, book_id, file_id)
values (671, 671, 671);
insert into book2file (id, book_id, file_id)
values (672, 672, 672);
insert into book2file (id, book_id, file_id)
values (673, 673, 673);
insert into book2file (id, book_id, file_id)
values (674, 674, 674);
insert into book2file (id, book_id, file_id)
values (675, 675, 675);
insert into book2file (id, book_id, file_id)
values (676, 676, 676);
insert into book2file (id, book_id, file_id)
values (677, 677, 677);
insert into book2file (id, book_id, file_id)
values (678, 678, 678);
insert into book2file (id, book_id, file_id)
values (679, 679, 679);
insert into book2file (id, book_id, file_id)
values (680, 680, 680);
insert into book2file (id, book_id, file_id)
values (681, 681, 681);
insert into book2file (id, book_id, file_id)
values (682, 682, 682);
insert into book2file (id, book_id, file_id)
values (683, 683, 683);
insert into book2file (id, book_id, file_id)
values (684, 684, 684);
insert into book2file (id, book_id, file_id)
values (685, 685, 685);
insert into book2file (id, book_id, file_id)
values (686, 686, 686);
insert into book2file (id, book_id, file_id)
values (687, 687, 687);
insert into book2file (id, book_id, file_id)
values (688, 688, 688);
insert into book2file (id, book_id, file_id)
values (689, 689, 689);
insert into book2file (id, book_id, file_id)
values (690, 690, 690);
insert into book2file (id, book_id, file_id)
values (691, 691, 691);
insert into book2file (id, book_id, file_id)
values (692, 692, 692);
insert into book2file (id, book_id, file_id)
values (693, 693, 693);
insert into book2file (id, book_id, file_id)
values (694, 694, 694);
insert into book2file (id, book_id, file_id)
values (695, 695, 695);
insert into book2file (id, book_id, file_id)
values (696, 696, 696);
insert into book2file (id, book_id, file_id)
values (697, 697, 697);
insert into book2file (id, book_id, file_id)
values (698, 698, 698);
insert into book2file (id, book_id, file_id)
values (699, 699, 699);
insert into book2file (id, book_id, file_id)
values (700, 700, 700);
insert into book2file (id, book_id, file_id)
values (701, 701, 701);
insert into book2file (id, book_id, file_id)
values (702, 702, 702);
insert into book2file (id, book_id, file_id)
values (703, 703, 703);
insert into book2file (id, book_id, file_id)
values (704, 704, 704);
insert into book2file (id, book_id, file_id)
values (705, 705, 705);
insert into book2file (id, book_id, file_id)
values (706, 706, 706);
insert into book2file (id, book_id, file_id)
values (707, 707, 707);
insert into book2file (id, book_id, file_id)
values (708, 708, 708);
insert into book2file (id, book_id, file_id)
values (709, 709, 709);
insert into book2file (id, book_id, file_id)
values (710, 710, 710);
insert into book2file (id, book_id, file_id)
values (711, 711, 711);
insert into book2file (id, book_id, file_id)
values (712, 712, 712);
insert into book2file (id, book_id, file_id)
values (713, 713, 713);
insert into book2file (id, book_id, file_id)
values (714, 714, 714);
insert into book2file (id, book_id, file_id)
values (715, 715, 715);
insert into book2file (id, book_id, file_id)
values (716, 716, 716);
insert into book2file (id, book_id, file_id)
values (717, 717, 717);
insert into book2file (id, book_id, file_id)
values (718, 718, 718);
insert into book2file (id, book_id, file_id)
values (719, 719, 719);
insert into book2file (id, book_id, file_id)
values (720, 720, 720);
insert into book2file (id, book_id, file_id)
values (721, 721, 721);
insert into book2file (id, book_id, file_id)
values (722, 722, 722);
insert into book2file (id, book_id, file_id)
values (723, 723, 723);
insert into book2file (id, book_id, file_id)
values (724, 724, 724);
insert into book2file (id, book_id, file_id)
values (725, 725, 725);
insert into book2file (id, book_id, file_id)
values (726, 726, 726);
insert into book2file (id, book_id, file_id)
values (727, 727, 727);
insert into book2file (id, book_id, file_id)
values (728, 728, 728);
insert into book2file (id, book_id, file_id)
values (729, 729, 729);
insert into book2file (id, book_id, file_id)
values (730, 730, 730);
insert into book2file (id, book_id, file_id)
values (731, 731, 731);
insert into book2file (id, book_id, file_id)
values (732, 732, 732);
insert into book2file (id, book_id, file_id)
values (733, 733, 733);
insert into book2file (id, book_id, file_id)
values (734, 734, 734);
insert into book2file (id, book_id, file_id)
values (735, 735, 735);
insert into book2file (id, book_id, file_id)
values (736, 736, 736);
insert into book2file (id, book_id, file_id)
values (737, 737, 737);
insert into book2file (id, book_id, file_id)
values (738, 738, 738);
insert into book2file (id, book_id, file_id)
values (739, 739, 739);
insert into book2file (id, book_id, file_id)
values (740, 740, 740);
insert into book2file (id, book_id, file_id)
values (741, 741, 741);
insert into book2file (id, book_id, file_id)
values (742, 742, 742);
insert into book2file (id, book_id, file_id)
values (743, 743, 743);
insert into book2file (id, book_id, file_id)
values (744, 744, 744);
insert into book2file (id, book_id, file_id)
values (745, 745, 745);
insert into book2file (id, book_id, file_id)
values (746, 746, 746);
insert into book2file (id, book_id, file_id)
values (747, 747, 747);
insert into book2file (id, book_id, file_id)
values (748, 748, 748);
insert into book2file (id, book_id, file_id)
values (749, 749, 749);
insert into book2file (id, book_id, file_id)
values (750, 750, 750);
insert into book2file (id, book_id, file_id)
values (751, 751, 751);
insert into book2file (id, book_id, file_id)
values (752, 752, 752);
insert into book2file (id, book_id, file_id)
values (753, 753, 753);
insert into book2file (id, book_id, file_id)
values (754, 754, 754);
insert into book2file (id, book_id, file_id)
values (755, 755, 755);
insert into book2file (id, book_id, file_id)
values (756, 756, 756);
insert into book2file (id, book_id, file_id)
values (757, 757, 757);
insert into book2file (id, book_id, file_id)
values (758, 758, 758);
insert into book2file (id, book_id, file_id)
values (759, 759, 759);
insert into book2file (id, book_id, file_id)
values (760, 760, 760);
insert into book2file (id, book_id, file_id)
values (761, 761, 761);
insert into book2file (id, book_id, file_id)
values (762, 762, 762);
insert into book2file (id, book_id, file_id)
values (763, 763, 763);
insert into book2file (id, book_id, file_id)
values (764, 764, 764);
insert into book2file (id, book_id, file_id)
values (765, 765, 765);
insert into book2file (id, book_id, file_id)
values (766, 766, 766);
insert into book2file (id, book_id, file_id)
values (767, 767, 767);
insert into book2file (id, book_id, file_id)
values (768, 768, 768);
insert into book2file (id, book_id, file_id)
values (769, 769, 769);
insert into book2file (id, book_id, file_id)
values (770, 770, 770);
insert into book2file (id, book_id, file_id)
values (771, 771, 771);
insert into book2file (id, book_id, file_id)
values (772, 772, 772);
insert into book2file (id, book_id, file_id)
values (773, 773, 773);
insert into book2file (id, book_id, file_id)
values (774, 774, 774);
insert into book2file (id, book_id, file_id)
values (775, 775, 775);
insert into book2file (id, book_id, file_id)
values (776, 776, 776);
insert into book2file (id, book_id, file_id)
values (777, 777, 777);
insert into book2file (id, book_id, file_id)
values (778, 778, 778);
insert into book2file (id, book_id, file_id)
values (779, 779, 779);
insert into book2file (id, book_id, file_id)
values (780, 780, 780);
insert into book2file (id, book_id, file_id)
values (781, 781, 781);
insert into book2file (id, book_id, file_id)
values (782, 782, 782);
insert into book2file (id, book_id, file_id)
values (783, 783, 783);
insert into book2file (id, book_id, file_id)
values (784, 784, 784);
insert into book2file (id, book_id, file_id)
values (785, 785, 785);
insert into book2file (id, book_id, file_id)
values (786, 786, 786);
insert into book2file (id, book_id, file_id)
values (787, 787, 787);
insert into book2file (id, book_id, file_id)
values (788, 788, 788);
insert into book2file (id, book_id, file_id)
values (789, 789, 789);
insert into book2file (id, book_id, file_id)
values (790, 790, 790);
insert into book2file (id, book_id, file_id)
values (791, 791, 791);
insert into book2file (id, book_id, file_id)
values (792, 792, 792);
insert into book2file (id, book_id, file_id)
values (793, 793, 793);
insert into book2file (id, book_id, file_id)
values (794, 794, 794);
insert into book2file (id, book_id, file_id)
values (795, 795, 795);
insert into book2file (id, book_id, file_id)
values (796, 796, 796);
insert into book2file (id, book_id, file_id)
values (797, 797, 797);
insert into book2file (id, book_id, file_id)
values (798, 798, 798);
insert into book2file (id, book_id, file_id)
values (799, 799, 799);
insert into book2file (id, book_id, file_id)
values (800, 800, 800);
insert into book2file (id, book_id, file_id)
values (801, 801, 801);
insert into book2file (id, book_id, file_id)
values (802, 802, 802);
insert into book2file (id, book_id, file_id)
values (803, 803, 803);
insert into book2file (id, book_id, file_id)
values (804, 804, 804);
insert into book2file (id, book_id, file_id)
values (805, 805, 805);
insert into book2file (id, book_id, file_id)
values (806, 806, 806);
insert into book2file (id, book_id, file_id)
values (807, 807, 807);
insert into book2file (id, book_id, file_id)
values (808, 808, 808);
insert into book2file (id, book_id, file_id)
values (809, 809, 809);
insert into book2file (id, book_id, file_id)
values (810, 810, 810);
insert into book2file (id, book_id, file_id)
values (811, 811, 811);
insert into book2file (id, book_id, file_id)
values (812, 812, 812);
insert into book2file (id, book_id, file_id)
values (813, 813, 813);
insert into book2file (id, book_id, file_id)
values (814, 814, 814);
insert into book2file (id, book_id, file_id)
values (815, 815, 815);
insert into book2file (id, book_id, file_id)
values (816, 816, 816);
insert into book2file (id, book_id, file_id)
values (817, 817, 817);
insert into book2file (id, book_id, file_id)
values (818, 818, 818);
insert into book2file (id, book_id, file_id)
values (819, 819, 819);
insert into book2file (id, book_id, file_id)
values (820, 820, 820);
insert into book2file (id, book_id, file_id)
values (821, 821, 821);
insert into book2file (id, book_id, file_id)
values (822, 822, 822);
insert into book2file (id, book_id, file_id)
values (823, 823, 823);
insert into book2file (id, book_id, file_id)
values (824, 824, 824);
insert into book2file (id, book_id, file_id)
values (825, 825, 825);
insert into book2file (id, book_id, file_id)
values (826, 826, 826);
insert into book2file (id, book_id, file_id)
values (827, 827, 827);
insert into book2file (id, book_id, file_id)
values (828, 828, 828);
insert into book2file (id, book_id, file_id)
values (829, 829, 829);
insert into book2file (id, book_id, file_id)
values (830, 830, 830);
insert into book2file (id, book_id, file_id)
values (831, 831, 831);
insert into book2file (id, book_id, file_id)
values (832, 832, 832);
insert into book2file (id, book_id, file_id)
values (833, 833, 833);
insert into book2file (id, book_id, file_id)
values (834, 834, 834);
insert into book2file (id, book_id, file_id)
values (835, 835, 835);
insert into book2file (id, book_id, file_id)
values (836, 836, 836);
insert into book2file (id, book_id, file_id)
values (837, 837, 837);
insert into book2file (id, book_id, file_id)
values (838, 838, 838);
insert into book2file (id, book_id, file_id)
values (839, 839, 839);
insert into book2file (id, book_id, file_id)
values (840, 840, 840);
insert into book2file (id, book_id, file_id)
values (841, 841, 841);
insert into book2file (id, book_id, file_id)
values (842, 842, 842);
insert into book2file (id, book_id, file_id)
values (843, 843, 843);
insert into book2file (id, book_id, file_id)
values (844, 844, 844);
insert into book2file (id, book_id, file_id)
values (845, 845, 845);
insert into book2file (id, book_id, file_id)
values (846, 846, 846);
insert into book2file (id, book_id, file_id)
values (847, 847, 847);
insert into book2file (id, book_id, file_id)
values (848, 848, 848);
insert into book2file (id, book_id, file_id)
values (849, 849, 849);
insert into book2file (id, book_id, file_id)
values (850, 850, 850);
insert into book2file (id, book_id, file_id)
values (851, 851, 851);
insert into book2file (id, book_id, file_id)
values (852, 852, 852);
insert into book2file (id, book_id, file_id)
values (853, 853, 853);
insert into book2file (id, book_id, file_id)
values (854, 854, 854);
insert into book2file (id, book_id, file_id)
values (855, 855, 855);
insert into book2file (id, book_id, file_id)
values (856, 856, 856);
insert into book2file (id, book_id, file_id)
values (857, 857, 857);
insert into book2file (id, book_id, file_id)
values (858, 858, 858);
insert into book2file (id, book_id, file_id)
values (859, 859, 859);
insert into book2file (id, book_id, file_id)
values (860, 860, 860);
insert into book2file (id, book_id, file_id)
values (861, 861, 861);
insert into book2file (id, book_id, file_id)
values (862, 862, 862);
insert into book2file (id, book_id, file_id)
values (863, 863, 863);
insert into book2file (id, book_id, file_id)
values (864, 864, 864);
insert into book2file (id, book_id, file_id)
values (865, 865, 865);
insert into book2file (id, book_id, file_id)
values (866, 866, 866);
insert into book2file (id, book_id, file_id)
values (867, 867, 867);
insert into book2file (id, book_id, file_id)
values (868, 868, 868);
insert into book2file (id, book_id, file_id)
values (869, 869, 869);
insert into book2file (id, book_id, file_id)
values (870, 870, 870);
insert into book2file (id, book_id, file_id)
values (871, 871, 871);
insert into book2file (id, book_id, file_id)
values (872, 872, 872);
insert into book2file (id, book_id, file_id)
values (873, 873, 873);
insert into book2file (id, book_id, file_id)
values (874, 874, 874);
insert into book2file (id, book_id, file_id)
values (875, 875, 875);
insert into book2file (id, book_id, file_id)
values (876, 876, 876);
insert into book2file (id, book_id, file_id)
values (877, 877, 877);
insert into book2file (id, book_id, file_id)
values (878, 878, 878);
insert into book2file (id, book_id, file_id)
values (879, 879, 879);
insert into book2file (id, book_id, file_id)
values (880, 880, 880);
insert into book2file (id, book_id, file_id)
values (881, 881, 881);
insert into book2file (id, book_id, file_id)
values (882, 882, 882);
insert into book2file (id, book_id, file_id)
values (883, 883, 883);
insert into book2file (id, book_id, file_id)
values (884, 884, 884);
insert into book2file (id, book_id, file_id)
values (885, 885, 885);
insert into book2file (id, book_id, file_id)
values (886, 886, 886);
insert into book2file (id, book_id, file_id)
values (887, 887, 887);
insert into book2file (id, book_id, file_id)
values (888, 888, 888);
insert into book2file (id, book_id, file_id)
values (889, 889, 889);
insert into book2file (id, book_id, file_id)
values (890, 890, 890);
insert into book2file (id, book_id, file_id)
values (891, 891, 891);
insert into book2file (id, book_id, file_id)
values (892, 892, 892);
insert into book2file (id, book_id, file_id)
values (893, 893, 893);
insert into book2file (id, book_id, file_id)
values (894, 894, 894);
insert into book2file (id, book_id, file_id)
values (895, 895, 895);
insert into book2file (id, book_id, file_id)
values (896, 896, 896);
insert into book2file (id, book_id, file_id)
values (897, 897, 897);
insert into book2file (id, book_id, file_id)
values (898, 898, 898);
insert into book2file (id, book_id, file_id)
values (899, 899, 899);
insert into book2file (id, book_id, file_id)
values (900, 900, 900);
insert into book2file (id, book_id, file_id)
values (901, 901, 901);
insert into book2file (id, book_id, file_id)
values (902, 902, 902);
insert into book2file (id, book_id, file_id)
values (903, 903, 903);
insert into book2file (id, book_id, file_id)
values (904, 904, 904);
insert into book2file (id, book_id, file_id)
values (905, 905, 905);
insert into book2file (id, book_id, file_id)
values (906, 906, 906);
insert into book2file (id, book_id, file_id)
values (907, 907, 907);
insert into book2file (id, book_id, file_id)
values (908, 908, 908);
insert into book2file (id, book_id, file_id)
values (909, 909, 909);
insert into book2file (id, book_id, file_id)
values (910, 910, 910);
insert into book2file (id, book_id, file_id)
values (911, 911, 911);
insert into book2file (id, book_id, file_id)
values (912, 912, 912);
insert into book2file (id, book_id, file_id)
values (913, 913, 913);
insert into book2file (id, book_id, file_id)
values (914, 914, 914);
insert into book2file (id, book_id, file_id)
values (915, 915, 915);
insert into book2file (id, book_id, file_id)
values (916, 916, 916);
insert into book2file (id, book_id, file_id)
values (917, 917, 917);
insert into book2file (id, book_id, file_id)
values (918, 918, 918);
insert into book2file (id, book_id, file_id)
values (919, 919, 919);
insert into book2file (id, book_id, file_id)
values (920, 920, 920);
insert into book2file (id, book_id, file_id)
values (921, 921, 921);
insert into book2file (id, book_id, file_id)
values (922, 922, 922);
insert into book2file (id, book_id, file_id)
values (923, 923, 923);
insert into book2file (id, book_id, file_id)
values (924, 924, 924);
insert into book2file (id, book_id, file_id)
values (925, 925, 925);
insert into book2file (id, book_id, file_id)
values (926, 926, 926);
insert into book2file (id, book_id, file_id)
values (927, 927, 927);
insert into book2file (id, book_id, file_id)
values (928, 928, 928);
insert into book2file (id, book_id, file_id)
values (929, 929, 929);
insert into book2file (id, book_id, file_id)
values (930, 930, 930);
insert into book2file (id, book_id, file_id)
values (931, 931, 931);
insert into book2file (id, book_id, file_id)
values (932, 932, 932);
insert into book2file (id, book_id, file_id)
values (933, 933, 933);
insert into book2file (id, book_id, file_id)
values (934, 934, 934);
insert into book2file (id, book_id, file_id)
values (935, 935, 935);
insert into book2file (id, book_id, file_id)
values (936, 936, 936);
insert into book2file (id, book_id, file_id)
values (937, 937, 937);
insert into book2file (id, book_id, file_id)
values (938, 938, 938);
insert into book2file (id, book_id, file_id)
values (939, 939, 939);
insert into book2file (id, book_id, file_id)
values (940, 940, 940);
insert into book2file (id, book_id, file_id)
values (941, 941, 941);
insert into book2file (id, book_id, file_id)
values (942, 942, 942);
insert into book2file (id, book_id, file_id)
values (943, 943, 943);
insert into book2file (id, book_id, file_id)
values (944, 944, 944);
insert into book2file (id, book_id, file_id)
values (945, 945, 945);
insert into book2file (id, book_id, file_id)
values (946, 946, 946);
insert into book2file (id, book_id, file_id)
values (947, 947, 947);
insert into book2file (id, book_id, file_id)
values (948, 948, 948);
insert into book2file (id, book_id, file_id)
values (949, 949, 949);
insert into book2file (id, book_id, file_id)
values (950, 950, 950);
insert into book2file (id, book_id, file_id)
values (951, 951, 951);
insert into book2file (id, book_id, file_id)
values (952, 952, 952);
insert into book2file (id, book_id, file_id)
values (953, 953, 953);
insert into book2file (id, book_id, file_id)
values (954, 954, 954);
insert into book2file (id, book_id, file_id)
values (955, 955, 955);
insert into book2file (id, book_id, file_id)
values (956, 956, 956);
insert into book2file (id, book_id, file_id)
values (957, 957, 957);
insert into book2file (id, book_id, file_id)
values (958, 958, 958);
insert into book2file (id, book_id, file_id)
values (959, 959, 959);
insert into book2file (id, book_id, file_id)
values (960, 960, 960);
insert into book2file (id, book_id, file_id)
values (961, 961, 961);
insert into book2file (id, book_id, file_id)
values (962, 962, 962);
insert into book2file (id, book_id, file_id)
values (963, 963, 963);
insert into book2file (id, book_id, file_id)
values (964, 964, 964);
insert into book2file (id, book_id, file_id)
values (965, 965, 965);
insert into book2file (id, book_id, file_id)
values (966, 966, 966);
insert into book2file (id, book_id, file_id)
values (967, 967, 967);
insert into book2file (id, book_id, file_id)
values (968, 968, 968);
insert into book2file (id, book_id, file_id)
values (969, 969, 969);
insert into book2file (id, book_id, file_id)
values (970, 970, 970);
insert into book2file (id, book_id, file_id)
values (971, 971, 971);
insert into book2file (id, book_id, file_id)
values (972, 972, 972);
insert into book2file (id, book_id, file_id)
values (973, 973, 973);
insert into book2file (id, book_id, file_id)
values (974, 974, 974);
insert into book2file (id, book_id, file_id)
values (975, 975, 975);
insert into book2file (id, book_id, file_id)
values (976, 976, 976);
insert into book2file (id, book_id, file_id)
values (977, 977, 977);
insert into book2file (id, book_id, file_id)
values (978, 978, 978);
insert into book2file (id, book_id, file_id)
values (979, 979, 979);
insert into book2file (id, book_id, file_id)
values (980, 980, 980);
insert into book2file (id, book_id, file_id)
values (981, 981, 981);
insert into book2file (id, book_id, file_id)
values (982, 982, 982);
insert into book2file (id, book_id, file_id)
values (983, 983, 983);
insert into book2file (id, book_id, file_id)
values (984, 984, 984);
insert into book2file (id, book_id, file_id)
values (985, 985, 985);
insert into book2file (id, book_id, file_id)
values (986, 986, 986);
insert into book2file (id, book_id, file_id)
values (987, 987, 987);
insert into book2file (id, book_id, file_id)
values (988, 988, 988);
insert into book2file (id, book_id, file_id)
values (989, 989, 989);
insert into book2file (id, book_id, file_id)
values (990, 990, 990);
insert into book2file (id, book_id, file_id)
values (991, 991, 991);
insert into book2file (id, book_id, file_id)
values (992, 992, 992);
insert into book2file (id, book_id, file_id)
values (993, 993, 993);
insert into book2file (id, book_id, file_id)
values (994, 994, 994);
insert into book2file (id, book_id, file_id)
values (995, 995, 995);
insert into book2file (id, book_id, file_id)
values (996, 996, 996);
insert into book2file (id, book_id, file_id)
values (997, 997, 997);
insert into book2file (id, book_id, file_id)
values (998, 998, 998);
insert into book2file (id, book_id, file_id)
values (999, 999, 999);
insert into book2file (id, book_id, file_id)
values (1000, 1000, 1000);
