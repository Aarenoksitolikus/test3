-- сгенерированные тестовые данные

INSERT INTO notes (title, content, created_at, updated_at, deleted_at) VALUES
    ('Grocery list', 'Milk, eggs, bread, butter, coffee', extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000, null),
    ('Meeting notes', 'Discuss Q2 roadmap, assign tasks, set deadlines', extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000, null),
    ('Book recommendations', 'Clean Code, The Pragmatic Programmer, Designing Data-Intensive Applications', extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000, null),
    ('Workout plan', 'Monday: chest, Wednesday: back, Friday: legs', extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000, null),
    ('Deleted note', 'This note has been removed', extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000, extract(epoch from now())::bigint * 1000);
