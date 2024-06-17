package com.example.nzgeneration.global.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class NFT extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051610f18380380610f18833981810160405281019061003191906101a4565b815f908161003f9190610427565b50806001908161004f9190610427565b5050506104f6565b5f604051905090565b5f80fd5b5f80fd5b5f80fd5b5f80fd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6100b682610070565b810181811067ffffffffffffffff821117156100d5576100d4610080565b5b80604052505050565b5f6100e7610057565b90506100f382826100ad565b919050565b5f67ffffffffffffffff82111561011257610111610080565b5b61011b82610070565b9050602081019050919050565b8281835e5f83830152505050565b5f610148610143846100f8565b6100de565b9050828152602081018484840111156101645761016361006c565b5b61016f848285610128565b509392505050565b5f82601f83011261018b5761018a610068565b5b815161019b848260208601610136565b91505092915050565b5f80604083850312156101ba576101b9610060565b5b5f83015167ffffffffffffffff8111156101d7576101d6610064565b5b6101e385828601610177565b925050602083015167ffffffffffffffff81111561020457610203610064565b5b61021085828601610177565b9150509250929050565b5f81519050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061026857607f821691505b60208210810361027b5761027a610224565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026102dd7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826102a2565b6102e786836102a2565b95508019841693508086168417925050509392505050565b5f819050919050565b5f819050919050565b5f61032b610326610321846102ff565b610308565b6102ff565b9050919050565b5f819050919050565b61034483610311565b61035861035082610332565b8484546102ae565b825550505050565b5f90565b61036c610360565b61037781848461033b565b505050565b5b8181101561039a5761038f5f82610364565b60018101905061037d565b5050565b601f8211156103df576103b081610281565b6103b984610293565b810160208510156103c8578190505b6103dc6103d485610293565b83018261037c565b50505b505050565b5f82821c905092915050565b5f6103ff5f19846008026103e4565b1980831691505092915050565b5f61041783836103f0565b9150826002028217905092915050565b6104308261021a565b67ffffffffffffffff81111561044957610448610080565b5b6104538254610251565b61045e82828561039e565b5f60209050601f83116001811461048f575f841561047d578287015190505b610487858261040c565b8655506104ee565b601f19841661049d86610281565b5f5b828110156104c45784890151825560018201915060208501945060208101905061049f565b868310156104e157848901516104dd601f8916826103f0565b8355505b6001600288020188555050505b505050505050565b610a15806105035f395ff3fe608060405234801561000f575f80fd5b506004361061003f575f3560e01c806306fdde031461004357806395d89b4114610061578063d3fc98641461007f575b5f80fd5b61004b61009b565b60405161005891906103f0565b60405180910390f35b610069610126565b60405161007691906103f0565b60405180910390f35b610099600480360381019061009491906105da565b6101b2565b005b5f80546100a790610673565b80601f01602080910402602001604051908101604052809291908181526020018280546100d390610673565b801561011e5780601f106100f55761010080835404028352916020019161011e565b820191905f5260205f20905b81548152906001019060200180831161010157829003601f168201915b505050505081565b6001805461013390610673565b80601f016020809104026020016040519081016040528092919081815260200182805461015f90610673565b80156101aa5780601f10610181576101008083540402835291602001916101aa565b820191905f5260205f20905b81548152906001019060200180831161018d57829003601f168201915b505050505081565b5f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1603610220576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610217906106ed565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff1660025f8481526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16146102be576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102b590610755565b60405180910390fd5b8260025f8481526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060035f8481526020019081526020015f20908161032b9190610910565b50818373ffffffffffffffffffffffffffffffffffffffff167fe7cd4ce7f2a465edc730269a1305e8a48bad821e8fb7e152ec413829c01a53c48360405161037391906103f0565b60405180910390a3505050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f6103c282610380565b6103cc818561038a565b93506103dc81856020860161039a565b6103e5816103a8565b840191505092915050565b5f6020820190508181035f83015261040881846103b8565b905092915050565b5f604051905090565b5f80fd5b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61044a82610421565b9050919050565b61045a81610440565b8114610464575f80fd5b50565b5f8135905061047581610451565b92915050565b5f819050919050565b61048d8161047b565b8114610497575f80fd5b50565b5f813590506104a881610484565b92915050565b5f80fd5b5f80fd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6104ec826103a8565b810181811067ffffffffffffffff8211171561050b5761050a6104b6565b5b80604052505050565b5f61051d610410565b905061052982826104e3565b919050565b5f67ffffffffffffffff821115610548576105476104b6565b5b610551826103a8565b9050602081019050919050565b828183375f83830152505050565b5f61057e6105798461052e565b610514565b90508281526020810184848401111561059a576105996104b2565b5b6105a584828561055e565b509392505050565b5f82601f8301126105c1576105c06104ae565b5b81356105d184826020860161056c565b91505092915050565b5f805f606084860312156105f1576105f0610419565b5b5f6105fe86828701610467565b935050602061060f8682870161049a565b925050604084013567ffffffffffffffff8111156106305761062f61041d565b5b61063c868287016105ad565b9150509250925092565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061068a57607f821691505b60208210810361069d5761069c610646565b5b50919050565b7f4d696e7420746f20746865207a65726f206164647265737300000000000000005f82015250565b5f6106d760188361038a565b91506106e2826106a3565b602082019050919050565b5f6020820190508181035f830152610704816106cb565b9050919050565b7f546f6b656e20616c7265616479206d696e7465640000000000000000000000005f82015250565b5f61073f60148361038a565b915061074a8261070b565b602082019050919050565b5f6020820190508181035f83015261076c81610733565b9050919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026107cf7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610794565b6107d98683610794565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61081461080f61080a8461047b565b6107f1565b61047b565b9050919050565b5f819050919050565b61082d836107fa565b6108416108398261081b565b8484546107a0565b825550505050565b5f90565b610855610849565b610860818484610824565b505050565b5b81811015610883576108785f8261084d565b600181019050610866565b5050565b601f8211156108c85761089981610773565b6108a284610785565b810160208510156108b1578190505b6108c56108bd85610785565b830182610865565b50505b505050565b5f82821c905092915050565b5f6108e85f19846008026108cd565b1980831691505092915050565b5f61090083836108d9565b9150826002028217905092915050565b61091982610380565b67ffffffffffffffff811115610932576109316104b6565b5b61093c8254610673565b610947828285610887565b5f60209050601f831160018114610978575f8415610966578287015190505b61097085826108f5565b8655506109d7565b601f19841661098686610773565b5f5b828110156109ad57848901518255600182019150602085019450602081019050610988565b868310156109ca57848901516109c6601f8916826108d9565b8355505b6001600288020188555050505b50505050505056fea2646970667358221220739e5fddd5f06386d1a2c575c9f2d193fdf22bca8ad497980fdcade528b0d0ee64736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final Event MINTED_EVENT = new Event("Minted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected NFT(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NFT(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger tokenId,
            String tokenURI) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new org.web3j.abi.datatypes.Utf8String(tokenURI)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

//    public static List<MintedEventResponse> getMintedEvents(TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINTED_EVENT, transactionReceipt);
//        ArrayList<MintedEventResponse> responses = new ArrayList<MintedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            MintedEventResponse typedResponse = new MintedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
//            typedResponse.tokenURI = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static MintedEventResponse getMintedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINTED_EVENT, log);
        MintedEventResponse typedResponse = new MintedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenURI = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MintedEventResponse> mintedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMintedEventFromLog(log));
    }

    public Flowable<MintedEventResponse> mintedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINTED_EVENT));
        return mintedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static NFT load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new NFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NFT load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NFT load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new NFT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NFT load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NFT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NFT> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _name, String _symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol)));
        return deployRemoteCall(NFT.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<NFT> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _name, String _symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol)));
        return deployRemoteCall(NFT.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFT> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, String _name, String _symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol)));
        return deployRemoteCall(NFT.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<NFT> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _name, String _symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol)));
        return deployRemoteCall(NFT.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

//    public static void linkLibraries(List<Contract.LinkReference> references) {
//        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
//    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class MintedEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger tokenId;

        public String tokenURI;
    }
}
