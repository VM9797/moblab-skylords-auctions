package hu.vm.bme.skylordsauctions

import org.junit.Assert

fun <T> assertCollectionEquals(expected: Collection<T>, actual: Collection<T>) {
    Assert.assertEquals(expected.size, actual.size)
    assert(expected.containsAll(actual))
}