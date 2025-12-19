# Online Food Ordering System

## Project Overview
A complete online food ordering system built with Java, JavaFX, and MySQL demonstrating Object-Oriented Programming principles and enterprise software development best practices.

## Features
✅ **Menu Management**
- View 23+ menu items with prices in USD
- Browse different food categories
- Real-time menu updates

✅ **Shopping Cart**
- Add/remove items from cart
- Calculate totals with tax
- Persistent cart state

✅ **Order Management**
- Place orders with customer details
- Order status tracking
- Complete order history

✅ **Coupon System**
- Percentage-based discounts (SAVE10, SAVE20, WELCOME)
- Fixed-amount discounts (FLAT5, FLAT15, BIGORDER)
- Expiry date validation
- Automatic discount calculation

✅ **User Management**
- Customer registration
- User authentication
- Profile management with address and phone
- Inheritance: Customer extends User

## Technology Stack
- **Language**: Java 17
- **Build Tool**: Maven 3.9.11
- **UI Framework**: JavaFX 21.0.1
- **Database**: MySQL 8.2.0
- **Testing**: JUnit 5.10.1, Mockito 5.7.0
- **Security**: BCrypt password hashing
- **Version Control**: Git/GitHub

## OOP Principles Demonstrated

### 1. Inheritance ✅
- `Customer` extends `User` class
- Proper constructor chaining with `super()`
- Method inheritance and overriding
- **Tests**: InheritanceTest.java (6 tests)

### 2. Encapsulation ✅
- Private fields with public getters/setters
- Validation in setters (address, phone, price, rating)
- Immutable fields (ID, email)
- Controlled access to sensitive data
- **Tests**: EncapsulationTest.java (10 tests)

### 3. Polymorphism ✅
- `List<User>` can hold both User and Customer objects
- Interface references (Orderable) for menu items
- Type checking with instanceof
- Downcasting for specific behavior
- **Tests**: PolymorphismTest.java (10 tests)

### 4. Interface Implementation ✅
- `Orderable` interface for menu items
- Contract enforcement (getId, getName, getPrice, getDescription, isAvailable)
- Enables loose coupling and dependency injection
- **Tests**: InterfaceTest.java (10 tests)

## Testing
**Total: 83 tests passing ✅**

### Unit Tests (47 tests)
- Model Tests: User, Customer, MenuItem, Order, Restaurant, Coupon
- Repository Tests: CRUD operations, data integrity
- Coupon Tests: Validation, discount calculation, expiry checking

### OOP Principle Tests (36 tests)
- InheritanceTest: Customer extends User (6 tests)
- EncapsulationTest: Field access control, validation (10 tests)
- PolymorphismTest: Type casting, collections (10 tests)
- InterfaceTest: Orderable implementation (10 tests)

## Available Coupons
| Code | Type | Value | Max Discount | Valid Until |
|------|------|-------|--------------|-------------|
| SAVE10 | Percentage | 10% | $50 | Dec 31, 2024 |
| SAVE20 | Percentage | 20% | $100 | Dec 31, 2024 |
| FLAT5 | Fixed | $5 | N/A | Dec 31, 2024 |
| FLAT15 | Fixed | $15 | N/A | Dec 31, 2024 |
| WELCOME | Percentage | 15% | $30 | Dec 31, 2024 |
| BIGORDER | Fixed | $20 | N/A | Dec 31, 2024 |

## Package Structure
```
com.foodordering/
├── model/              # Domain models
│   ├── User.java
│   ├── Customer.java (extends User)
│   ├── MenuItem.java (implements Orderable)
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Restaurant.java
│   ├── Coupon.java
│   ├── CouponType.java (enum)
│   ├── OrderStatus.java (enum)
│   └── PaymentMethod.java (enum)
├── repository/         # Data access layer
│   ├── UserRepository.java
│   ├── MenuItemRepository.java
│   ├── OrderRepository.java
│   ├── RestaurantRepository.java
│   └── CouponRepository.java
├── service/           # Business logic
│   ├── MenuService.java
│   ├── OrderService.java
│   └── CouponService.java
├── controller/        # JavaFX controllers
│   └── MenuController.java
├── database/          # Database utilities
│   ├── DatabaseConnection.java
│   ├── DataInitializer.java
│   ├── CouponDataInitializer.java
│   └── CouponTableInitializer.java
└── Main.java          # Application entry point
```

## How to Run

### Prerequisites
- Java Development Kit (JDK) 17+
- Maven 3.6+
- MySQL 8.0+
- Git

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE food_ordering;
```

2. Update credentials in `DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/food_ordering";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

3. Tables are created automatically on first run

### Run the Application
```bash
mvn clean javafx:run
```

### Run Tests
```bash
mvn test
```

### Build JAR
```bash
mvn clean package
```

## Git Commit History
This project demonstrates incremental development with atomic commits:

1. ✅ Initial project setup with models and repositories
2. ✅ Add service layer with business logic  
3. ✅ Create MenuController with JavaFX UI
4. ✅ Add DataInitializer with 23 menu items
5. ✅ Implement coupon system (model + repository)
6. ✅ Add coupon validation and discount logic
7. ✅ Create comprehensive OOP test suite (36 tests)
8. 🔄 Update README with project documentation

**Target: 20+ commits**

## Repository
**GitHub**: https://github.com/A7MED505/online-food-ordering.git

## Contributors
- Ahmed (GitHub: @A7MED505)

## License
Educational project for university coursework.
