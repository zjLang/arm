# 说明（来自《java实战》）

## 常用函数式接口

| 函数式接口             | 函数描述符          | 原始类型特化                                                                                                                                                                               |
|:------------------|:---------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Predicate<T>      | T->boolean     | IntPredicate,LongPredicate,DoublePredicate                                                                                                                                           |
| Consumer<T>       | T->void        | IntConsumer,LongConsumer, DoubleConsumer                                                                                                                                             |
| Function<T,R>     | T->R           | IntFunction<R>,IntToDoubleFunction,IntToLongFunction,LongFunction<R>,LongToDoubleFunction,LongToIntFunction,DoubleFunction<R>,ToIntFunction<T>,ToDoubleFunction<T>,ToLongFunction<T> |
| Supplier<T>       | ()->T          | BooleanSupplier,IntSupplier,LongSupplier, DoubleSupplier                                                                                                                             |
| UnaryOperator<T>  | T->T           | IntUnaryOperator,LongUnaryOperator,DoubleUnaryOperator                                                                                                                               |
| BinaryOperator<T> | (T,T)->T       | IntBinaryOperator,LongBinaryOperator,DoubleBinaryOperator                                                                                                                            |
| BiPredicate<L,R>  | (L,R)->boolean | --                                                                                                                                                                                   |
| BiConsumer<T,U>   | (T,U)->void    | ObjIntConsumer<T>,ObjLongConsumer<T>,ObjDoubleConsumer<T>                                                                                                                            |
| BiFunction<T,U,R> | (T,U)->R       | ToIntBiFunction<T,U>,ToLongBiFunction<T,U>,ToDoubleBiFunction<T,U>                                                                                                                   |
