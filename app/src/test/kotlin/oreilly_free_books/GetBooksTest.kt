package oreilly_free_books

import io.reactivex.Single
import oreilly_free_books.domain.model.Book
import oreilly_free_books.domain.repository.Repository
import oreilly_free_books.domain.usecase.GetBooks
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class GetBooksTest {

  companion object {

    private val ANY_TITLE = "_title"
    private val ANY_DESCRIPTION = "_description"
    private val ANY_IMAGE = "_image"
    private val ANY_URL = "_url"
    private val ANY_CATEGORY = "_category"
    private val ANY_SUBCATEGORY = "_subCategory"


    @ClassRule @JvmField
    val blockingRxAndroidTestRule = RxAndroidTestRule()
  }

  @Rule @JvmField
  var mockitoRule = MockitoJUnit.rule()!!

  @Mock lateinit var repository: Repository
  private lateinit var getBooks: GetBooks

  @Before
  fun setUp() {
    getBooks = GetBooks(repository)
  }

  @Test
  fun shouldReturnABookList() {

    val bookList = givenABookListWithData()
    getBooks.execute()
        .test()
        .assertNoErrors()
        .assertValue {
          it.size == bookList.size && it[0].title == bookList[0].title
        }
  }




  private fun givenABookListWithData(): List<Book> {
    val bookList = ArrayList<Book>()
    (1..10).mapTo(bookList) {
      Book(it.toString(), ANY_TITLE, ANY_DESCRIPTION, ANY_IMAGE, ANY_URL, ANY_CATEGORY, ANY_SUBCATEGORY, emptyList())
    }
    given(getBooks.execute()).willReturn(Single.just(bookList))
    return bookList
  }

}