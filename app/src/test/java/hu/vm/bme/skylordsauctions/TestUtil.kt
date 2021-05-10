package hu.vm.bme.skylordsauctions

import org.junit.Assert

fun <T> assertCollectionEquals(expected: Collection<T>, actual: Collection<T>) {
    Assert.assertEquals(expected.size, actual.size)
    assert(expected.containsAll(actual))
}

fun <T> assertCollectionContains(element: T, collection: Collection<T>) {
    Assert.assertTrue(collection.contains(element))
}