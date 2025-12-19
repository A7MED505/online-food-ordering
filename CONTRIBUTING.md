# Contributing to Online Food Ordering System

## Code of Conduct
This project follows standard open-source contribution guidelines. Be respectful and constructive in all interactions.

## How to Contribute

### Reporting Bugs
- Use the GitHub Issues tab
- Provide detailed description of the bug
- Include steps to reproduce
- Mention your environment (Java version, OS, etc.)

### Suggesting Features
- Open an issue with the "enhancement" label
- Describe the feature and its use case
- Explain why it would be valuable

### Pull Request Process

1. **Fork the Repository**
   ```bash
   git clone https://github.com/A7MED505/online-food-ordering.git
   ```

2. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the coding standards below
   - Write tests for new features
   - Update documentation as needed

4. **Test Your Changes**
   ```bash
   mvn test
   mvn clean javafx:run
   ```

5. **Commit Your Changes**
   - Use descriptive commit messages
   - Follow the commit message format below

6. **Push and Create PR**
   ```bash
   git push origin feature/your-feature-name
   ```

## Coding Standards

### Java Code Style
- Use 4 spaces for indentation (no tabs)
- Follow Java naming conventions:
  - Classes: `PascalCase`
  - Methods/Variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
- Maximum line length: 120 characters
- Always include JavaDoc for public methods

### Example:
```java
/**
 * Calculates the total price with discount applied.
 * 
 * @param originalPrice the original price before discount
 * @param discountPercent the discount percentage (0-100)
 * @return the final price after discount
 */
public double calculateDiscountedPrice(double originalPrice, double discountPercent) {
    if (discountPercent < 0 || discountPercent > 100) {
        throw new IllegalArgumentException("Discount must be between 0 and 100");
    }
    return originalPrice * (1 - discountPercent / 100);
}
```

### Testing Requirements
- Write unit tests for all new features
- Maintain at least 80% code coverage
- All tests must pass before PR is merged
- Use JUnit 5 for testing
- Use descriptive test names

### Example Test:
```java
@Test
@DisplayName("Should calculate discount correctly for valid percentage")
void testCalculateDiscountWithValidPercentage() {
    double result = calculator.calculateDiscountedPrice(100.0, 10.0);
    assertEquals(90.0, result, 0.01);
}
```

## Commit Message Format

### Pattern:
```
<type>: <description>

[optional body]
```

### Types:
- **Feature**: New feature implementation
- **Fix**: Bug fix
- **Test**: Adding or updating tests
- **Docs**: Documentation changes
- **Refactor**: Code refactoring without changing functionality
- **Style**: Code style/formatting changes
- **Config**: Configuration file changes
- **Perf**: Performance improvements

### Examples:
```
Feature: Add restaurant rating display in UI

Fix: Resolve null pointer exception in coupon validation

Test: Add integration tests for order service

Docs: Update README with installation instructions
```

## Project Structure

### Adding New Features
1. **Model Layer**: Add domain objects in `com.foodordering.model`
2. **Repository Layer**: Add data access in `com.foodordering.repository`
3. **Service Layer**: Add business logic in `com.foodordering.service`
4. **Controller Layer**: Add UI controllers in `com.foodordering.controller`

### OOP Requirements
All code must demonstrate proper OOP principles:
- **Encapsulation**: Private fields with getters/setters
- **Inheritance**: Proper use of class hierarchies
- **Polymorphism**: Interface-based design where appropriate
- **Abstraction**: Clear separation of concerns

## Database Changes
- Always provide migration scripts for schema changes
- Test database changes with sample data
- Document any new tables or columns
- Ensure backward compatibility when possible

## Documentation
- Update README.md for user-facing changes
- Add JavaDoc comments for all public APIs
- Include code examples for complex features
- Update UML diagrams if architecture changes

## Testing Checklist
Before submitting a PR, ensure:
- [ ] All existing tests pass
- [ ] New tests added for new features
- [ ] Code coverage maintained/improved
- [ ] Manual testing completed
- [ ] No compiler warnings
- [ ] Documentation updated

## Need Help?
- Check existing issues and pull requests
- Review the project README
- Ask questions in issue comments
- Contact maintainers: [@A7MED505](https://github.com/A7MED505)

## License
By contributing, you agree that your contributions will be licensed under the same license as the project.
