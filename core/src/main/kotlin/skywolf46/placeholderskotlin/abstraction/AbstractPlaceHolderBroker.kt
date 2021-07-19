package skywolf46.placeholderskotlin.abstraction

import skywolf46.placeholderskotlin.analyzer.StringAnalyzer
import skywolf46.placeholderskotlin.data.WrappedString

abstract class AbstractPlaceHolderBroker :
    AbstractAnalyzeBroker<String, AbstractPlaceHolder, WrappedString, StringAnalyzer>()