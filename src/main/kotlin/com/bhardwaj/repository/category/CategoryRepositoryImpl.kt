package com.bhardwaj.repository.category

import com.bhardwaj.models.Category
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CategoryRepositoryImpl(
    database: CoroutineDatabase,
) : CategoryRepository {

    private val categoryTable = database.getCollection<Category>()

    override suspend fun getCategoryById(categoryId: String): Category? {
        return categoryTable.findOne(filter = Category::categoryId eq categoryId)
    }

    override suspend fun insertCategory(category: Category): Boolean {
        return categoryTable.insertOne(document = category).wasAcknowledged()
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return categoryTable.deleteOne(filter = Category::categoryId eq categoryId).wasAcknowledged()
    }

    override suspend fun updateCategory(category: Category): Category? {
        categoryTable.updateOne(filter = Category::categoryId eq category.categoryId, target = category)
        return categoryTable.findOne(filter = Category::categoryId eq category.categoryId)
    }

    override suspend fun getAllCategory(): List<Category> {
        return categoryTable.find().ascendingSort(Category::categoryName).toList()
    }
}