특정 레이어에서 에러가 발생하도록 하고 싶은 경우

### [성공] mockito-kotlin

```kotlin
@Test
fun successError() = runBlockingTest {
    doReturn(Throwable("Error"))
        .`when`(apiService)
        .contributors(any(), any())

    viewModel.ping()

    val value = viewModel.errorEvent.getOrAwaitValue()
    assertEquals(value, "error")
}
```

### [실패] mockito

```kotlin
@Test
fun failError() = runBlockingTest {
    // Issues : https://github.com/mockito/mockito/issues/1166?fbclid=IwAR0AAo-Ze5uX2JEZqc2a0N2k44mBxNP7J1jUnhDwGDoq8dA5UEnwdiUO0qA
    whenever(apiService.contributors(any(), any()))
        .doThrow(Throwable("Error"))

    viewModel.ping()

    val value = viewModel.errorEvent.getOrAwaitValue()
    assertEquals(value, "error")
}
```

Issues : https://github.com/mockito/mockito/issues/1166?fbclid=IwAR0AAo-Ze5uX2JEZqc2a0N2k44mBxNP7J1jUnhDwGDoq8dA5UEnwdiUO0qA
