package com.zhizhen.ybb;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void jsTest(){
        String json = "{\n" +
                "    \"status\": 0,\n" +
                "    \"statusInfo\": \"\",\n" +
                "    \"data\": {\n" +
                "        \"dashboard\": {\n" +
                "            \"1\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 276,\n" +
                "                \"percent\": \"0.94793240829784\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"2\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 194,\n" +
                "                \"percent\": \"0.66630031597747\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"3\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 768,\n" +
                "                \"percent\": \"2.6377249622201\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"4\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 886,\n" +
                "                \"percent\": \"3.0430004121445\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"5\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 942,\n" +
                "                \"percent\": \"3.2353345239731\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"6\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 892,\n" +
                "                \"percent\": \"3.0636076384119\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"7\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 1576,\n" +
                "                \"percent\": \"5.4128314328891\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"8\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 2068,\n" +
                "                \"percent\": \"7.1026239868114\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"9\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 1712,\n" +
                "                \"percent\": \"5.8799285616156\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"10\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 2756,\n" +
                "                \"percent\": \"9.4655859321335\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"11\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 2092,\n" +
                "                \"percent\": \"7.1850528918808\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"12\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 2466,\n" +
                "                \"percent\": \"8.4695699958786\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"13\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 3892,\n" +
                "                \"percent\": \"13.367220772084\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"14\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 4602,\n" +
                "                \"percent\": \"15.805742547053\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"15\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 2904,\n" +
                "                \"percent\": \"9.9738975133947\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"16\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 442,\n" +
                "                \"percent\": \"1.518065668361\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"17\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 544,\n" +
                "                \"percent\": \"1.8683885149059\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"18\": {\n" +
                "                \"total_time\": 29116,\n" +
                "                \"duration\": 104,\n" +
                "                \"percent\": \"0.3571919219673\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"histogram\": {\n" +
                "            \"1\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"2\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"3\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"4\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"5\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"6\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"7\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"8\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"9\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"10\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"11\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"12\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"13\": {\n" +
                "                \"mild_time\": \"20\",\n" +
                "                \"middle_time\": \"94\",\n" +
                "                \"serious_time\": \"38\",\n" +
                "                \"mild_time_percent\": \"0.033333333333333\",\n" +
                "                \"middle_time_percent\": \"0.15666666666667\",\n" +
                "                \"serious_time_percent\": \"0.063333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"14\": {\n" +
                "                \"mild_time\": \"134\",\n" +
                "                \"middle_time\": \"354\",\n" +
                "                \"serious_time\": \"2\",\n" +
                "                \"mild_time_percent\": \"0.22333333333333\",\n" +
                "                \"middle_time_percent\": \"0.59\",\n" +
                "                \"serious_time_percent\": \"0.0033333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"15\": {\n" +
                "                \"mild_time\": \"256\",\n" +
                "                \"middle_time\": \"16\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.42666666666667\",\n" +
                "                \"middle_time_percent\": \"0.026666666666667\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"16\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"17\": {\n" +
                "                \"mild_time\": \"74\",\n" +
                "                \"middle_time\": \"68\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.12333333333333\",\n" +
                "                \"middle_time_percent\": \"0.11333333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"18\": {\n" +
                "                \"mild_time\": \"8\",\n" +
                "                \"middle_time\": \"16\",\n" +
                "                \"serious_time\": \"32\",\n" +
                "                \"mild_time_percent\": \"0.013333333333333\",\n" +
                "                \"middle_time_percent\": \"0.026666666666667\",\n" +
                "                \"serious_time_percent\": \"0.053333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"19\": {\n" +
                "                \"mild_time\": \"68\",\n" +
                "                \"middle_time\": \"96\",\n" +
                "                \"serious_time\": \"121\",\n" +
                "                \"mild_time_percent\": \"0.11333333333333\",\n" +
                "                \"middle_time_percent\": \"0.16\",\n" +
                "                \"serious_time_percent\": \"0.20166666666667\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"20\": {\n" +
                "                \"mild_time\": \"170\",\n" +
                "                \"middle_time\": \"150\",\n" +
                "                \"serious_time\": \"113\",\n" +
                "                \"mild_time_percent\": \"0.28333333333333\",\n" +
                "                \"middle_time_percent\": \"0.25\",\n" +
                "                \"serious_time_percent\": \"0.18833333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"21\": {\n" +
                "                \"mild_time\": \"116\",\n" +
                "                \"middle_time\": \"110\",\n" +
                "                \"serious_time\": \"280\",\n" +
                "                \"mild_time_percent\": \"0.19333333333333\",\n" +
                "                \"middle_time_percent\": \"0.18333333333333\",\n" +
                "                \"serious_time_percent\": \"0.46666666666667\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"22\": {\n" +
                "                \"mild_time\": \"111\",\n" +
                "                \"middle_time\": \"40\",\n" +
                "                \"serious_time\": \"14\",\n" +
                "                \"mild_time_percent\": \"0.185\",\n" +
                "                \"middle_time_percent\": \"0.066666666666667\",\n" +
                "                \"serious_time_percent\": \"0.023333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"23\": {\n" +
                "                \"mild_time\": \"135\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.225\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"24\": {\n" +
                "                \"mild_time\": \"54\",\n" +
                "                \"middle_time\": \"119\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.09\",\n" +
                "                \"middle_time_percent\": \"0.19833333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"25\": {\n" +
                "                \"mild_time\": \"64\",\n" +
                "                \"middle_time\": \"111\",\n" +
                "                \"serious_time\": \"44\",\n" +
                "                \"mild_time_percent\": \"0.10666666666667\",\n" +
                "                \"middle_time_percent\": \"0.185\",\n" +
                "                \"serious_time_percent\": \"0.073333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"26\": {\n" +
                "                \"mild_time\": \"40\",\n" +
                "                \"middle_time\": \"90\",\n" +
                "                \"serious_time\": \"74\",\n" +
                "                \"mild_time_percent\": \"0.066666666666667\",\n" +
                "                \"middle_time_percent\": \"0.15\",\n" +
                "                \"serious_time_percent\": \"0.12333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"27\": {\n" +
                "                \"mild_time\": \"114\",\n" +
                "                \"middle_time\": \"64\",\n" +
                "                \"serious_time\": \"66\",\n" +
                "                \"mild_time_percent\": \"0.19\",\n" +
                "                \"middle_time_percent\": \"0.10666666666667\",\n" +
                "                \"serious_time_percent\": \"0.11\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"28\": {\n" +
                "                \"mild_time\": \"42\",\n" +
                "                \"middle_time\": \"28\",\n" +
                "                \"serious_time\": \"2\",\n" +
                "                \"mild_time_percent\": \"0.07\",\n" +
                "                \"middle_time_percent\": \"0.046666666666667\",\n" +
                "                \"serious_time_percent\": \"0.0033333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"29\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"2\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0.0033333333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"30\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"14\",\n" +
                "                \"serious_time\": \"6\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0.023333333333333\",\n" +
                "                \"serious_time_percent\": \"0.01\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"31\": {\n" +
                "                \"mild_time\": \"172\",\n" +
                "                \"middle_time\": \"170\",\n" +
                "                \"serious_time\": \"203\",\n" +
                "                \"mild_time_percent\": \"0.28666666666667\",\n" +
                "                \"middle_time_percent\": \"0.28333333333333\",\n" +
                "                \"serious_time_percent\": \"0.33833333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"32\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"12\",\n" +
                "                \"serious_time\": \"588\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.02\",\n" +
                "                \"serious_time_percent\": \"0.98\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"33\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"22\",\n" +
                "                \"serious_time\": \"576\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.036666666666667\",\n" +
                "                \"serious_time_percent\": \"0.96\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"34\": {\n" +
                "                \"mild_time\": \"58\",\n" +
                "                \"middle_time\": \"94\",\n" +
                "                \"serious_time\": \"300\",\n" +
                "                \"mild_time_percent\": \"0.096666666666667\",\n" +
                "                \"middle_time_percent\": \"0.15666666666667\",\n" +
                "                \"serious_time_percent\": \"0.5\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"35\": {\n" +
                "                \"mild_time\": \"138\",\n" +
                "                \"middle_time\": \"252\",\n" +
                "                \"serious_time\": \"192\",\n" +
                "                \"mild_time_percent\": \"0.23\",\n" +
                "                \"middle_time_percent\": \"0.42\",\n" +
                "                \"serious_time_percent\": \"0.32\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"36\": {\n" +
                "                \"mild_time\": \"275\",\n" +
                "                \"middle_time\": \"156\",\n" +
                "                \"serious_time\": \"163\",\n" +
                "                \"mild_time_percent\": \"0.45833333333333\",\n" +
                "                \"middle_time_percent\": \"0.26\",\n" +
                "                \"serious_time_percent\": \"0.27166666666667\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"37\": {\n" +
                "                \"mild_time\": \"151\",\n" +
                "                \"middle_time\": \"146\",\n" +
                "                \"serious_time\": \"2\",\n" +
                "                \"mild_time_percent\": \"0.25166666666667\",\n" +
                "                \"middle_time_percent\": \"0.24333333333333\",\n" +
                "                \"serious_time_percent\": \"0.0033333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"38\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"39\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"6\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0.01\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"40\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"41\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"42\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"43\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"44\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"2\",\n" +
                "                \"serious_time\": \"2\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.0033333333333333\",\n" +
                "                \"serious_time_percent\": \"0.0033333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"45\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"46\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"4\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0.0066666666666667\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"47\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"48\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"2\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.0033333333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"49\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"50\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"51\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"52\": {\n" +
                "                \"mild_time\": \"6\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.01\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"53\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"54\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"2\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0.0033333333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"55\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"56\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"57\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"58\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"59\": {\n" +
                "                \"mild_time\": \"2\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0.0033333333333333\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"60\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"61\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"0\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"62\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"2\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.0033333333333333\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"63\": {\n" +
                "                \"mild_time\": \"10\",\n" +
                "                \"middle_time\": \"32\",\n" +
                "                \"serious_time\": \"12\",\n" +
                "                \"mild_time_percent\": \"0.016666666666667\",\n" +
                "                \"middle_time_percent\": \"0.053333333333333\",\n" +
                "                \"serious_time_percent\": \"0.02\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"64\": {\n" +
                "                \"mild_time\": \"40\",\n" +
                "                \"middle_time\": \"66\",\n" +
                "                \"serious_time\": \"152\",\n" +
                "                \"mild_time_percent\": \"0.066666666666667\",\n" +
                "                \"middle_time_percent\": \"0.11\",\n" +
                "                \"serious_time_percent\": \"0.25333333333333\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            },\n" +
                "            \"65\": {\n" +
                "                \"mild_time\": \"0\",\n" +
                "                \"middle_time\": \"6\",\n" +
                "                \"serious_time\": \"0\",\n" +
                "                \"mild_time_percent\": \"0\",\n" +
                "                \"middle_time_percent\": \"0.01\",\n" +
                "                \"serious_time_percent\": \"0\",\n" +
                "                \"date\": \"2017-05-31\",\n" +
                "                \"pid\": \"19\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"sit_info\": {\n" +
                "            \"sit_time\": \"4598\",\n" +
                "            \"sit_time_percent\": \"15.792004396208\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        bean2 bo = JSON.parseObject(json, bean2.class);
        Map<String, Object> map = new HashMap();
        Map<String, Object> histogramMap = new HashMap();
        Map<String, Object> sit_infoMap = new HashMap();
        Map<String, Object> dashboardMap = new HashMap();
        Object dashboard = "", histogram = "", sit_info = "";
        map = JSON.parseObject(bo.getData(), Map.class);

        for (Entry<String, Object> s : map.entrySet()) {
            if (s.getKey().equals("dashboard")) {
                dashboard = s.getValue();
            } else if (s.getKey().equals("histogram")) {
                histogram = s.getValue();
            } else if (s.getKey().equals("sit_info")) {
                sit_info = s.getValue();
            }
        }

        List<bean_3> bean_3s = new ArrayList<>();
        bean_3 bean3;
        dashboardMap = JSON.parseObject(dashboard.toString(), Map.class);
        for (Entry<String, Object> s : dashboardMap.entrySet()) {
            bean3 = JSON.parseObject(s.getValue().toString(), bean_3.class);
            bean3.setKey(s.getKey());
            bean_3s.add(bean3);
        }

        System.out.println("dashboard= >Value " + bean_3s.size());

        histogramMap = JSON.parseObject(histogram.toString(), Map.class);
        for (Entry<String, Object> s : histogramMap.entrySet()) {
//            System.out.println("histogram= >Key  " + s.getKey());
//            System.out.println("histogram= >Value " + s.getValue());
        }


        sit_infoMap = JSON.parseObject(sit_info.toString(), Map.class);
        for (Entry<String, Object> s : sit_infoMap.entrySet()) {
//            System.out.println("sit_info= >Key  " + s.getKey());
//            System.out.println("sit_info= >Value " + s.getValue());
        }

    }

}