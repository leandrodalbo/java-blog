# Entry 15: Relational Databases

## Modeling entities and relationships

Our domain: teachers, students, and courses.

- A teacher teaches many courses, but each course has one teacher: **one-to-many**.

- A student can enroll in many courses, and a course can have many students: **many-to-many**.

```
Teacher (1) ---- teaches ----> (N) Course

Student (N) <--- enrolled in ---> (N) Course
```

A many-to-many relationship can't be stored directly in two tables. We need a junction (or join) table, holding one row per pairing.

```
Student (1) ---- (N) Enrollment (N) ---- (1) Course
```

## Why normalize

Put everything into one flat table, and duplication follows.

| student | course   | teacher | teacher_department |
|---------|----------|---------|---------------------|
| Ana     | Math 101 | Diaz    | Science             |
| Ana     | Physics  | Osei    | Science             |
| Ben     | Math 101 | Diaz    | Science             |

Diaz and Science show up in every row where Diaz teaches.

That duplication causes three problems: update, insertion, and deletion anomalies.

Normalization is the process of splitting a table like this into smaller ones so each fact is stored exactly once. The formal steps are called normal forms.

### 1NF: atomic values

Every column must hold a single value, never a list.

### 2NF: no partial dependency

Applies once a table has a composite key (more than one column together forming the key). Every other column must depend on the whole key, not just part of it.

### 3NF: no transitive dependency

A non-key column can't depend on another non-key column instead of the key.

Applied to the flat table above: `teacher` depends only on `course`, not on the full `(student, course)` key, so 2NF moves it out. `teacher_department` depends on `teacher`, a non-key column, not directly on `course`, so 3NF moves it out too.

After all three steps:

```
Teacher(id, name, department)
Course(id, title, teacher_id)
Student(id, name)
Enrollment(student_id, course_id)
```

Each fact now lives in exactly one place. The cost: reading "Ana's courses with teacher department" now takes three joins instead of one flat read.

That's the normalized versus denormalized trade-off. Normalized favors correctness and cheap, safe writes.

Denormalized favors fast reads but might lose consistency.

## Querying across the tables

SQL is built on relational algebra. Some of its clauses map directly onto operators from it:

- σ (selection): filter rows, `WHERE`
- π (projection): pick columns, `SELECT`
- ⋈ (join): combine relations on a condition, most often a foreign key matching the primary key it references, `JOIN`
- ∩ (intersection): rows present in both relations, both relations must have the same columns, `INTERSECT`

Projection, just the student names:

`π name (Student)`

```sql
SELECT name FROM Student;
```

Join, each student with their teacher and course:

`π student.name, teacher.name, course.title (Student ⋈₍id=student_id₎ Enrollment ⋈₍id=course_id₎ Course ⋈₍id=teacher_id₎ Teacher)`

```sql
SELECT s.name AS student, t.name AS teacher, c.title AS course
FROM Student s
JOIN Enrollment e ON s.id = e.student_id
JOIN Course c ON e.course_id = c.id
JOIN Teacher t ON c.teacher_id = t.id;
```

Intersection, students enrolled in both Math and Physics:

`π student_id (σ course_id='Math101' Enrollment) ∩ π student_id (σ course_id='Physics101' Enrollment)`


```
   Math students         Physics students
  +------------+      +--------------------+
  |    Ben     |      |                    |
  |        +---+------+---+                |
  |        |    Ana       |                |
  +--------+---------------+---------------+
                    ^
              enrolled in both
```

```sql
SELECT s.name
FROM Student s
WHERE s.id IN (SELECT e0.student_id
               FROM Enrollment e0
               WHERE e0.course_id = 'Math101'
               INTERSECT
               SELECT e1.student_id
               FROM Enrollment e1
               WHERE e1.course_id = 'Physics101')
```
