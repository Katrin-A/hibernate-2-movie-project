# Hibernate movie project

## Implemented Features

### Customer Management
- Create a new customer
- Automatically create a city if it does not exist
- Create customer address

### Rental Management
- Rent a film
- Return a film
- Automatically create payment for rental

### Film Management
- Add a new film
- Add categories to a film
- Add actors to a film
- Create inventory entry for a store
- Support custom enum conversion for:
    - Rating
    - Special Features

---

## Technologies

- Java
- Hibernate ORM
- MySQL
- Lombok
- Maven

---

## Database Design Issues (Sakila)

### 1. Missing Foreign Key
`film_text.film_id` does not have a foreign key to `film.film_id`.

### 2. Missing Foreign Key
`customer.store_id` does not have a foreign key to `store.store_id`.

### 3. Unused Column
`film.original_language_id` contains only `NULL` values and is effectively unused.

### 4. MySQL-Specific YEAR Type
`film.release_year` uses MySQL-specific `YEAR` type, which reduces portability.

### 5. Inconsistent Identifier Types
The schema uses different numeric types for identifiers:
- `TINYINT`
- `SMALLINT`
- `MEDIUMINT`

This complicates Hibernate mapping and schema validation.  
A more modern design would use a unified identifier type such as:
- `INT`
- or `BIGINT`

for both primary keys and foreign keys.

### 6. MySQL-Specific ENUM and SET Types
The database uses MySQL-specific `ENUM` and `SET` column types.

This required custom Hibernate `AttributeConverter` implementations for:
- `Rating`
- `Special Features`

### 7. Join Tables Contain Additional Columns
The tables:
- `film_actor`
- `film_category`

contain additional column `last_update`.

In a stricter ORM design these tables could be modeled as separate entities instead of plain `@ManyToMany` relationships.

---