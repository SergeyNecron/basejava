INSERT INTO resume (uuid, full_name) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
  ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3');

INSERT INTO contact (id, resume_uuid, type, value) VALUES
  (1, '7de882da-02f2-4d16-8daa-60660aaf4071', 'PHONE', '88005353535'),
  (2, '7de882da-02f2-4d16-8daa-60660aaf4071', 'MOBILE', '84285653535'),
  (3, '7de882da-02f2-4d16-8daa-60660aaf4071', 'HOME_PHONE', '891735532428'),
  (4, '7de882da-02f2-4d16-8daa-60660aaf4071', 'SKYPE', 'vasa.ivanov'),
  (5, '7de882da-02f2-4d16-8daa-60660aaf4071', 'EMAIL', 'vasa@mail.ru'),
  (6, '7de882da-02f2-4d16-8daa-60660aaf4071', 'LINKEDIN', 'профиль LINKEDIN'),
  (7, '7de882da-02f2-4d16-8daa-60660aaf4071', 'GITHUB', 'профиль GITHUB'),
  (8, '7de882da-02f2-4d16-8daa-60660aaf4071', 'STATCKOVERFLOW', 'профиль StackOverflow'),
  (9, '7de882da-02f2-4d16-8daa-60660aaf4071', 'HABRAHABR', 'профиль Habrahabr'),
  (10, '7de882da-02f2-4d16-8daa-60660aaf4071', 'HOME_PHONE', 'vasa.ivanov.ru');
