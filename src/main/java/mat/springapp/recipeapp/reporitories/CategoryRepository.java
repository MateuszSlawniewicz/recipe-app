package mat.springapp.recipeapp.reporitories;

import mat.springapp.recipeapp.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
