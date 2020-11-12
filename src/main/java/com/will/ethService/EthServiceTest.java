package com.will.ethService;

import org.apache.commons.codec.digest.Crypt;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.Web3j;
import org.web3j.crypto.*;
import org.web3j.crypto.WalletFile.Crypto;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.contracts.*;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;
import org.web3j.crypto.Hash;

import java.io.Console;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class EthServiceTest {
	
	// 계약 주소 세팅
		String contract = "0x8d0C09C0033c12004F97A7Cd40dE0C3bF5C94296";
				

    @Test
    public void getEthClientVersionSync() throws Exception
    {
        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        System.out.println(web3ClientVersion.getWeb3ClientVersion());
    }


    @Test
    public void getEthClientVersionASync() throws Exception
    {
        Web3j web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545")); 
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        System.out.println(web3ClientVersion.getWeb3ClientVersion());
    }

    @Test
    public void getEthClientVersionRx() throws Exception
    {
        Web3j web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545")); 
        web3.web3ClientVersion().flowable().subscribe(x -> {
            System.out.println(x.getWeb3ClientVersion());
        });

        Thread.sleep(3000);
    }

    @Test
    public void sendFileUpload() throws Exception
    {
    	String path="C:\\Users\\lee\\Documents\\dfdfdf.txt";
    	Web3j web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
    	 
    	 // File을 읽어서 해싱하는 기능
    	byte[] file_content = Files.readAllBytes(Paths.get(path));
    	 
    	byte[] hashResult = Hash.sha3(file_content);
    	
    	
    	List inputParams = new ArrayList();
    	List outputParams = new ArrayList();
    	inputParams.add(new Bytes32(hashResult));
    	inputParams.add(new Address("0xBCce3693F3cA86346aD62Bd7e2c9a5B3F121d6EE"));
    	outputParams.add(new TypeReference<Bool>(){});
    	// 컨트랙트 의 호출할 기능과 파라메터를 설정한다.
    	Function contractFunction = new Function("fileUpload",
    			inputParams,
    			outputParams   
			);
    	 
    	// 전송할 트랜잰션을 만든다.
    	EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x8d0C09C0033c12004F97A7Cd40dE0C3bF5C94296", DefaultBlockParameterName.LATEST).send();
    	
    	BigInteger nonce= ethGetTransactionCount.getTransactionCount();
    	
    	String from = "0xBCce3693F3cA86346aD62Bd7e2c9a5B3F121d6EE";
    	String to = contract;
    	BigInteger gasPrice = new BigInteger("10000000000");
    	BigInteger gasLimit = new BigInteger("200000");
    	String init = FunctionEncoder.encode(contractFunction);
    	System.out.println(init);
    	
    			
    	RawTransaction rawTransaction = RawTransaction.createTransaction(
    			nonce, 
    			gasPrice, 
    			gasLimit,
    			to,
    			init);
    	
    	// 프라이빗키를 가져온다.		
    	Credentials credentials = Credentials.create("8b0b42f39170c561e28fad49a25c2922718311c905ea29d4ee24a71854b5f267");
    	// 트랜잭션에 프라이빗 키를 통해 서명한다
    	
    	String signedTransaction = Numeric.toHexString(
    			TransactionEncoder.signMessage(rawTransaction, credentials));
    	
    	// 서명한 트랜잭션을 네트워크에 전송한다.
    	try {
    		Request<?, EthSendTransaction> rq = web3.ethSendRawTransaction(signedTransaction);
    		EthSendTransaction response = rq.send();
		    
		    String hash = response.getTransactionHash();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
    	 
    }
    	
}
    